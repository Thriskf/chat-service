package org.elteq.logic.activityLog.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*


@Serializable
data class ActivityLogDTO(
    var id: UUID? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,
    var email: String? = null,
    var phoneNumber: String? = null,
    var unit : Unit? = null,

    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null
) {
    override fun toString(): String {
        return "ActivityLogDTO(id=$id, firstName=$firstName, lastName=$lastName, otherName=$otherName, email=$email, phoneNumber=$phoneNumber, unit=$unit, createdOn=$createdOn, updatedOn=$updatedOn)"
    }

}