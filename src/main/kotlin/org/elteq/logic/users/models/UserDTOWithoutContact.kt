package org.elteq.logic.users.models

import java.time.LocalDateTime
import java.util.*
import kotlinx.serialization.Serializable
import org.elteq.logic.users.enums.Gender
import org.elteq.logic.users.enums.Status


@Serializable
data class UserDTOWithoutContact(
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,
    var displayName: String? = null,
    var status: Status? = null,
    var gender: Gender? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null
)