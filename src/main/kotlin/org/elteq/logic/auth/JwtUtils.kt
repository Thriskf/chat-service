package org.elteq.logic.auth

import io.smallrye.jwt.auth.principal.JWTParser
import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.elteq.base.exception.AuthenticationException
import org.elteq.logic.auth.dtos.TokenPair
import org.elteq.logic.users.models.Users
import org.elteq.logic.users.service.UserService
import java.time.Duration
import java.time.Instant
import java.util.*

@ApplicationScoped
class JwtUtils {

    @ConfigProperty(name = "token.issuer")
    private lateinit var issuer: String

    @ConfigProperty(name = "token.expiration", defaultValue = "5")
    private lateinit var tokenExpiration: String

    @ConfigProperty(name = "token.refreshToken.expiration", defaultValue = "24")
    private lateinit var refreshTokenExpiration: String

    @Inject
    lateinit var jwtParser: JWTParser

    @Inject
    private lateinit var userService: UserService

    fun generateTokenPair(user: Users): TokenPair {
        val tokenExpiryDuration = Duration.ofHours(refreshTokenExpiration.toLong())
        val tokenExpiryTime = Instant.now().plus(tokenExpiryDuration)

        val refreshTokenExpiryDuration = Duration.ofHours(tokenExpiration.toLong())
        val refreshTokenExpiryTime = Instant.now().plus(refreshTokenExpiryDuration)

        val accessToken = generateAccessToken(user, tokenExpiryTime)
        val refreshToken = generateRefreshToken(user, refreshTokenExpiryTime)

        return TokenPair(accessToken, refreshToken, tokenExpiryTime, refreshTokenExpiryTime)
    }


    private fun generateAccessToken(user: Users, tokenExpiryTime: Instant): String {
        return Jwt.issuer(issuer)
            .upn(user.id)
            .subject(user.id)
            .claim("displayName", user.displayName)
            .claim("firstName", user.firstName)
            .claim("lastName", user.lastName)
            .claim("gender", user.gender)
            .claim("token_type", "access")
            .expiresAt(tokenExpiryTime)
            .sign()
    }

    private fun generateRefreshToken(user: Users, refreshTokenExpiryTime: Instant): String {
        return Jwt.issuer(issuer)
            .upn(user.id)
            .subject(user.id)
            .claim("token_type", "refresh")
            .expiresAt(refreshTokenExpiryTime)
            .sign()
    }

    fun validateRefreshToken(refreshToken: String): Boolean {
        return try {
            val jwt = jwtParser.parse(refreshToken)
            jwt.getClaim<String>("token_type") == "refresh" &&
                    jwt.expirationTime > Instant.now().epochSecond
        } catch (e: Exception) {
            false
        }
    }

    fun refreshAccessToken(refreshToken: String): TokenPair {
        val jwt = jwtParser.parse(refreshToken)

        if (jwt.getClaim<String>("token_type") != "refresh") {
            throw AuthenticationException("Invalid token type")
        }

        if (jwt.expirationTime <= Instant.now().epochSecond) {
            throw AuthenticationException("Refresh token expired")
        }

        val userId = jwt.subject ?: throw AuthenticationException("Invalid token subject")
        val user = userService.getById(userId) ?: throw AuthenticationException("User not found")

        return generateTokenPair(user)
    }


    fun generateToken(user: Users, email: String): String {
        val tokenExpiryDuration = Duration.ofHours(5)
        val expiryTime = Instant.now().plus(tokenExpiryDuration)

        return Jwt.issuer(issuer) // Set the issuer
            .upn(user.id) // Set the "upn" (User Principal Name) claim
            .subject(user.id) // Set the subject (username)
//            .groups(roles) // Add roles as groups
//            .claim("email", email) // Add custom claim for email
            .claim("displayName", user.displayName)
            .claim("firstName", user.firstName)
            .claim("lastName", user.lastName)
            .claim("gender", user.gender)
            .expiresAt(expiryTime) // Set token expiry time
            .sign() // Sign and generate the token
    }

    fun gen(roles: Set<String?>?, dto: Users, email: String, displayName: String): String {
        val tokenExpiryDuration = Duration.ofHours(5)
        val expiryTime = Instant.now().plus(tokenExpiryDuration)

        val jwtClaimBuilder = Jwt.claims()
        val builder2 = Jwt.claims("/tokenClaims.json")
        val builder3 = Jwt.claims(Collections.singletonMap<String, Any>("customClaim", "custom-value"))

        return jwtClaimBuilder.claim("customClaim", "custom-value").issuer(issuer)
            .upn(email)
            .subject(email)
            .groups(roles)
            .claim("email", email)
            .claim("displayName", displayName)
            .expiresAt(expiryTime)
            .innerSign().encrypt()
    }


//    companion object {
//        @Throws(Exception::class)
//        fun generateToken(username: String?, email: String?, roles: Set<String?>?): String {
//            val privateKey = "your-private-key-here" // Replace with your private key
//            val keyFactory = KeyFactory.getInstance("RSA")
//            val privateKeyBytes = Base64.getDecoder().decode(privateKey)
//            val key = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes))
//
//                    return Jwts.builder()
//                .setSubject(username)
//                .claim("user", email) // Add email to the token
//                .claim("roles", roles) // Add roles to the token
//                .setIssuer("https://your-issuer.com")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
//                .signWith(key, SignatureAlgorithm.RS256)
//                .compact();
//            return ""
//        }
//    }
}