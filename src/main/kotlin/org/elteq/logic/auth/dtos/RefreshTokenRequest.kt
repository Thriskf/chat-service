package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank
    var refreshToken: String?=null
)