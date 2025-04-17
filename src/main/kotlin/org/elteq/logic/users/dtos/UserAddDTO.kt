package org.elteq.logic.users.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber
import org.elteq.logic.users.enums.Gender

@Serializable
data class UserAddDTO(
    @NotBlank
    @Required
    var firstName: String? = null,

    @NotBlank
    @Required
    var lastName: String? = null,

    @NotBlank
    var gender: Gender? = null,

    @NotBlank
    var otherName: String? = null,

    @NotBlank
    var displayName: String? = null,

    @NotBlank
    @Email
    @Required
    var email: String? = null,

    @NotBlank
    @ValidPhoneNumber
    @Required
    var phoneNumber: String? = null,

//    @NotNull
//    var dateOfBirth: DoBDTO? = null
)