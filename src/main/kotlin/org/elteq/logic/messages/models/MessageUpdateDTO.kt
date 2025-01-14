package org.elteq.logic.messages.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MessageUpdateDTO (
    @NotBlank
    var id: String? = null,

    @Email
    var message: String? = null,
    )