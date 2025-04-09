package org.elteq.logic.auth

import io.smallrye.jwt.auth.principal.*
import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.context.control.ActivateRequestContext
import jakarta.enterprise.inject.Alternative
import jakarta.inject.Inject
import org.eclipse.microprofile.context.ManagedExecutor
import org.elteq.logic.users.models.Users
import org.elteq.logic.users.service.UserService
import org.jboss.logging.Logger
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.InvalidJwtException
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


@ApplicationScoped
@Alternative
@Priority(1)
class CustomJWTCallerPrincipalFactory(
    @Inject var userService: UserService,
    @Inject var executor: ManagedExecutor,
    private val logger: Logger,
) : JWTCallerPrincipalFactory() {
    private val blockingExecutor = Executors.newCachedThreadPool()

    @Throws(ParseException::class)
    override fun parse(token: String, authContextInfo: JWTAuthContextInfo): JWTCallerPrincipal {
        try {
            // First verify the token signature and basic claims
//            val verifiedJwt = jwtParser.verify(token, authContextInfo.issuedBy)

            // Then parse the claims
            val claims = try {
                val claimsPart = token.split(".")[1]
                val json = String(
                    Base64.getUrlDecoder().decode(claimsPart),
                    StandardCharsets.UTF_8
                )
                JwtClaims.parse(json)
            } catch (ex: Exception) {
                throw ParseException("Invalid JWT claims structure")
            }

            // Additional custom validation
            validateClaims(claims, authContextInfo)

            return DefaultJWTCallerPrincipal(claims)
        } catch (ex: InvalidJwtException) {
            throw ParseException("JWT verification failed: ${ex.message}")
        }
    }

    @ActivateRequestContext
    private fun validateClaims(claims: JwtClaims, authContextInfo: JWTAuthContextInfo) {
        // Validate required claims
        if (claims.subject.isNullOrEmpty()) {
            throw ParseException("Missing 'sub' claim")
        }

        // Validate issuer if configured
        authContextInfo.issuedBy?.let { expectedIssuer ->
            if (expectedIssuer != claims.issuer) {
                throw ParseException("Invalid issuer")
            }
        }

        // Validate expiration
        if (claims.expirationTime == null || Instant.ofEpochSecond(claims.expirationTime.value).isBefore(Instant.now())) {
            throw ParseException("Token expired")
        }

        //validate user token version
        val userId = claims.subject ?: throw ParseException("Subject is null")

        val user = runCatching {
            executor.submit<Users> {
                userService.getById(userId)
            }.get(3, TimeUnit.SECONDS)
        }.fold(
            onSuccess = {
                it
            },
            onFailure = {
                logger.error("error while validating user", it)
                throw ParseException("User validation failed: ${it.message}")

            }
        )


        val version = claims.getClaimValue("token_version") ?: throw ParseException("Null token version")

        val version1 = version as Number
        if (version1.toLong() != user.tokenVersion) {
            throw ParseException("Invalid token version")
        }
    }

}