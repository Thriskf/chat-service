package org.elteq.logic.messages.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class MessageDTO(
    var id: UUID? = null,
    var message: String? = null,
    var createdOn: LocalDateTime? = null,
    var updatedOn: LocalDateTime? = null
)