package org.elteq.logic.chatRoom.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatRoomAddMessageDTO (
    @NotBlank
    var id: String? = null,
    var message: String? = null,
    )