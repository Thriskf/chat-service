package org.elteq.logic.users.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber
import java.util.*

@Serializable
data class UserUpdateContactDTO(
    @NotBlank
    var id: UUID? = null,

    @Email
    var email: String? = null,

    @ValidPhoneNumber
    var phoneNumber: String? = null,
)