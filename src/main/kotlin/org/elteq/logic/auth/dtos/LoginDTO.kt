package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO(
    @NotBlank
    @Email
    val username: String? = null,

    @NotBlank
    val password: String? = null,

    )