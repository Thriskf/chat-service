package org.elteq.logic.activityLog.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber

import java.util.*

@Serializable
class UpdateActivityLogDTO (
    @NotBlank
    var id: UUID? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,

    @Email
    var email: String? = null,

    @ValidPhoneNumber
    var phoneNumber: String? = null,


    ) {
    override fun toString(): String {
        return "UpdateActivityLogDTO(id=$id, firstName=$firstName, lastName=$lastName, otherName=$otherName, email=$email, phoneNumber=$phoneNumber)"
    }
}