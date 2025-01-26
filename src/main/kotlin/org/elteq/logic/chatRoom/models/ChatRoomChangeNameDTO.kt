package org.elteq.logic.chatRoom.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidateArrayPhoneNumber

@Serializable
data class ChatRoomChangeNameDTO (
    @NotBlank
    var id: String? = null,

   @NotBlank
    var name:String? = null

    )