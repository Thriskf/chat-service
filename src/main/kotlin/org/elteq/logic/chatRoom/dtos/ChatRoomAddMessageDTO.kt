package org.elteq.logic.chatRoom.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomAddMessageDTO (
    @NotBlank
    var id: String? = null,
    var message: String? = null,
    )