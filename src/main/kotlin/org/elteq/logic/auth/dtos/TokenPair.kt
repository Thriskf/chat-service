package org.elteq.logic.auth.dtos

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiry: Instant,
    val refreshTokenExpiry: Instant,
)