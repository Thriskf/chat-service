package org.elteq.logic.auth

import io.smallrye.jwt.auth.principal.*
import jakarta.annotation.Priority
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Alternative
import org.jose4j.jwt.JwtClaims
import org.jose4j.jwt.consumer.InvalidJwtException
import java.nio.charset.StandardCharsets
import java.util.*


@ApplicationScoped
@Alternative
@Priority(1)
class CustomJWTCallerPrincipalFactory : JWTCallerPrincipalFactory() {
    @Throws(ParseException::class)
    override fun parse(token: String, authContextInfo: JWTAuthContextInfo): JWTCallerPrincipal {
        try {
            // Token has already been verified, parse the token claims only
            val json = String(
                Base64.getUrlDecoder().decode(token.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]),
                StandardCharsets.UTF_8
            )
            return DefaultJWTCallerPrincipal(JwtClaims.parse(json))
        } catch (ex: InvalidJwtException) {
            throw ParseException(ex.message)
        }
    }
}