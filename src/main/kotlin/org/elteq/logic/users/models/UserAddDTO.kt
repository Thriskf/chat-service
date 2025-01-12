package org.elteq.logic.users.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber
import org.elteq.logic.dob.models.DoBDTO

@Serializable
data class UserAddDTO(
    @NotBlank
    var firstName: String? = null,

    @NotBlank
    var lastName: String? = null,

    @NotBlank
    var otherName: String? = null,

    @NotBlank
    var displayName: String? = null,

    @NotBlank
    @Email
    var email: String? = null,

    @NotBlank
    @ValidPhoneNumber
    var phoneNumber: String? = null,

    @NotNull
    var dateOfBirth: DoBDTO? = null
)