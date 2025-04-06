package org.elteq.logic.auth

import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.elteq.logic.users.models.Users
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.time.Duration
import java.time.Instant
import java.util.*

@ApplicationScoped
class JwtUtils {

    @ConfigProperty(name = "token.issuer")
    private lateinit var issuer: String

    fun generateToken(user:Users,email: String): String {
        val tokenExpiryDuration = Duration.ofHours(5)
        val expiryTime = Instant.now().plus(tokenExpiryDuration)

        return Jwt.issuer(issuer) // Set the issuer
            .upn(email) // Set the "upn" (User Principal Name) claim
            .subject(email) // Set the subject (username)
//            .groups(roles) // Add roles as groups
            .claim("email", email) // Add custom claim for email
            .claim("displayName", user.displayName)
            .claim("firstName", user.firstName)
            .claim("lastName", user.lastName)
            .claim("gender", user.gender)
            .expiresAt(expiryTime) // Set token expiry time
            .sign() // Sign and generate the token
    }

    fun gen(roles: Set<String?>?, dto: Users,email:String,displayName:String): String {
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

    companion object {
        @Throws(Exception::class)
        fun generateToken(username: String?, email: String?, roles: Set<String?>?): String {
            val privateKey = "your-private-key-here" // Replace with your private key
            val keyFactory = KeyFactory.getInstance("RSA")
            val privateKeyBytes = Base64.getDecoder().decode(privateKey)
            val key = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes))

            //        return Jwts.builder()
//                .setSubject(username)
//                .claim("user", email) // Add email to the token
//                .claim("roles", roles) // Add roles to the token
//                .setIssuer("https://your-issuer.com")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
//                .signWith(key, SignatureAlgorithm.RS256)
//                .compact();
            return ""
        }
    }
}