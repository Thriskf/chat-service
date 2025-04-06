package org.elteq.logic.users.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO (
    @NotBlank
    @Email
    var username: String? = null,

    @NotBlank
    var password: String? = null,

)