package org.elteq.logic.chatRoom.models

import kotlinx.serialization.Serializable
import org.elteq.logic.messages.models.MessageDTO
import java.time.LocalDateTime
import java.util.*


@Serializable
data class ChatRoomDTO(
    var id: String? = null,
    var messages: MutableSet<MessageDTO>? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null,
)