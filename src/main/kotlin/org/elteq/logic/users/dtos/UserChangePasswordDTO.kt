package org.elteq.logic.users.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.logic.users.enums.Status

@Serializable
data class UserChangePasswordDTO(
    @NotBlank
    var userId: String? = null,

    @NotBlank
    var currentPassword: String? = null,

    @NotBlank
    var newPassword: String? = null,




)