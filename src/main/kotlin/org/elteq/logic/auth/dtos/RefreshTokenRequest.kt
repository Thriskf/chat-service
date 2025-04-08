package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable


@Serializable
data class RefreshTokenRequest(
    @NotBlank
    val refreshToken: String?
)