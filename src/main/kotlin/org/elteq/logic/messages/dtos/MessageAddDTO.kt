package org.elteq.logic.messages.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class MessageAddDTO(
    @NotBlank
    var message: String? = null,
)