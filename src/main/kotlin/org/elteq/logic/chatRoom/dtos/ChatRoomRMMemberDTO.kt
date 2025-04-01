package org.elteq.logic.chatRoom.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber

@Serializable
data class ChatRoomRMMemberDTO (
    @NotBlank
    var id: String? = null,

    @NotBlank
    @ValidPhoneNumber
    var phoneNumber: String? =null,

    )