package org.elteq.logic.chatRoom.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidateArrayPhoneNumber

@Serializable
data class ChatRoomAddDTO(
    @NotBlank
    @ValidateArrayPhoneNumber
    var phoneNumbers: MutableSet<String>? = mutableSetOf(),

    var name: String?=null,
)