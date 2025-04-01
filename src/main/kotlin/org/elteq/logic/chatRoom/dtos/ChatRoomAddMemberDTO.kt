package org.elteq.logic.chatRoom.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidateArrayPhoneNumber

@Serializable
data class ChatRoomAddMemberDTO (
    @NotBlank
    var roomId: String? = null,

    @NotBlank
    @ValidateArrayPhoneNumber
    var phoneNumbers: MutableSet<String>? = mutableSetOf(),

    )