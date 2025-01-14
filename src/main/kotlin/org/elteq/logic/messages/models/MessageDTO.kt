package org.elteq.logic.messages.models

import kotlinx.serialization.Serializable
import org.elteq.logic.users.models.UserDTOWithoutContact
import java.time.LocalDateTime

@Serializable
data class MessageDTO(
    var id: String? = null,
    var message: String? = null,
    var user: UserDTOWithoutContact? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null
)