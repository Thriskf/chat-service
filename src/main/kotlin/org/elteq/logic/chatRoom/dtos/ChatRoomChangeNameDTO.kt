package org.elteq.logic.chatRoom.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomChangeNameDTO (
    @NotBlank
    var id: String? = null,

   @NotBlank
    var name:String? = null

    )