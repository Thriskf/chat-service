package org.elteq.logic.messages.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class MessageUpdateDTO (
    @NotBlank
    var id: String? = null,

    @Email
    var message: String? = null,
    )