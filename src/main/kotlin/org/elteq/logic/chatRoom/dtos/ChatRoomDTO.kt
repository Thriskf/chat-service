package org.elteq.logic.chatRoom.dtos

import kotlinx.serialization.Serializable
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.messages.dtos.MessageDTO
import java.time.LocalDateTime


@Serializable
data class ChatRoomDTO(
    var id: String? = null,
    var messages: MutableSet<MessageDTO>? = null,
    var name: String? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null,
    var type: ChatRoomType? = null

)