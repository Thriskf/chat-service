package org.elteq.logic.chatRoom.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber

@Serializable
data class ChatRoomAddDTO(
    @NotBlank
    var phoneNumbers: MutableSet<PhoneNumberDTO>? = mutableSetOf(),
)

@Serializable
data class PhoneNumberDTO(
    @NotBlank
    @ValidPhoneNumber
    var phoneNumber: String? = null,
)