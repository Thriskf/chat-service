package org.elteq.logic.messages.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class MessageAddDTO(
    @NotBlank
    var message: String? = null,
)