package org.elteq.logic.users.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber

@Serializable
data class UserUpdateContactDTO(
    @NotBlank
    var id: String? = null,

    @Email
    var email: String? = null,

    @ValidPhoneNumber
    var phoneNumber: String? = null,
)