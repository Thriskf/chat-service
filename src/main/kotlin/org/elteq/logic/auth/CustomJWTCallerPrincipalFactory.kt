package org.elteq.logic.auth

import io.smallrye.jwt.auth.principal.*
import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Alternative
import jakarta.inject.Inject
import org.elteq.logic.users.service.UserService
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.InvalidJwtException
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.util.*


@ApplicationScoped
@Alternative
@Priority(1)
class CustomJWTCallerPrincipalFactory(
    @Inject var userService: UserService,
) : JWTCallerPrincipalFactory() {
//    @Throws(ParseException::class)
//    override fun parse(token: String, authContextInfo: JWTAuthContextInfo): JWTCallerPrincipal {
//        try {
//            // Token has already been verified, parse the token claims only
//            val json = String(
//                Base64.getUrlDecoder().decode(token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]),
//                StandardCharsets.UTF_8
//            )
//            return DefaultJWTCallerPrincipal(JwtClaims.parse(json))
//        } catch (ex: InvalidJwtException) {
//            throw ParseException(ex.message)
//        }
//    }


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

        //validate token version
        val userId = claims.subject ?: throw ParseException("Subject is null")
//         val user = runBlocking(Dispatchers.IO) {
//             userService.getById(userId)
//         }

        val user = userService.getById(userId)

        val version = claims.getClaimValue("token_version") ?: throw ParseException("Null token version")

        if (version as Int != user.tokenVersion) {
            throw ParseException("Invalid token version")
        }
    }

}