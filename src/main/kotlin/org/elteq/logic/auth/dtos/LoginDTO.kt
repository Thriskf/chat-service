package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Required

data class LoginDTO(
    @NotBlank
    @Email
    @Required
    var username: String? = null,

    @NotBlank
    @Required
    var password: String? = null,

    )