package org.elteq.logic.chatRoom.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber
import org.elteq.base.validators.ValidateArrayPhoneNumber

@Serializable
data class ChatRoomRMMemberDTO (
    @NotBlank
    var id: String? = null,

    @NotBlank
    @ValidPhoneNumber
    var phoneNumber: String? =null,

    )