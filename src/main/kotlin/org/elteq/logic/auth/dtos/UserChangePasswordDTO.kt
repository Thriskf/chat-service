package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class UserChangePasswordDTO(
    @NotBlank
    @Required
    var userId: String? = null,

    @NotBlank
    @Required
    var currentPassword: String? = null,

    @NotBlank
    @Required
    var newPassword: String? = null,




)