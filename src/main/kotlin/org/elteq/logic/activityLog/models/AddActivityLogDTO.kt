package org.elteq.logic.activityLog.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber


@Serializable
class AddActivityLogDTO(
    @NotBlank
    var firstName: String? = null,

    @NotBlank
    var lastName: String? = null,

    @NotBlank
    var otherName: String? = null,

    @NotBlank
    @Email
    var email: String? = null,


    @NotBlank
    @ValidPhoneNumber
    var phoneNumber: String? = null,
) {
    override fun toString(): String {
        return "AddActivityLogDTO(firstName=$firstName, lastName=$lastName, otherName=$otherName, email=$email, phoneNumber=$phoneNumber)"
    }
}