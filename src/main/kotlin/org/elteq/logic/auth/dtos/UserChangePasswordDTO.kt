package org.elteq.logic.auth.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class UserChangePasswordDTO(
    @NotBlank
    var userId: String? = null,

    @NotBlank
    var currentPassword: String? = null,

    @NotBlank
    var newPassword: String? = null,




)