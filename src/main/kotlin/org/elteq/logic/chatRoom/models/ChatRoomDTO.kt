package org.elteq.logic.chatRoom.models

import kotlinx.serialization.Serializable
import org.elteq.logic.messages.db.Messages
import java.time.LocalDateTime
import java.util.*


@Serializable
data class ChatRoomDTO(
    var id: UUID? = null,
    var messages: MutableSet<Messages>? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null,
)