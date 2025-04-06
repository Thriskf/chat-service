package org.elteq.logic.users.dtos

import java.time.LocalDateTime
import kotlinx.serialization.Serializable
import org.elteq.logic.contacts.models.Contact
import org.elteq.logic.users.enums.Gender
import org.elteq.logic.users.enums.Status


@Serializable
data class UserDTO(
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,
    var displayName: String? = null,
    var status: Status? = null,
    var contacts: Set<Contact>? = null,
    var gender: Gender? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null
)