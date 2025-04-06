package org.elteq.logic.users.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.logic.users.enums.Status

@Serializable
data class UserForgetPasswordDTO(
    @NotBlank
    @Email
    var email: String? = null,
)