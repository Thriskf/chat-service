package org.elteq.logic.chatRoom.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidateArrayPhoneNumber

@Serializable
data class ChatRoomAddMemberDTO (
    @NotBlank
    var id: String? = null,

    @NotBlank
    @ValidateArrayPhoneNumber
    var phoneNumbers: MutableSet<String>? = mutableSetOf(),

    )