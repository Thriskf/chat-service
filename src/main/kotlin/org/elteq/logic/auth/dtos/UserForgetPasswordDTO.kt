package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class UserForgetPasswordDTO(
    @NotBlank
    @Email
    var email: String? = null,
)