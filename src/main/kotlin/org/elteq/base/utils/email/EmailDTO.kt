package org.elteq.base.utils.email

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable


@Serializable
data class EmailDTO(
    @NotBlank
    var recipientEmail: String?,
    var recipientName :String? = null,
    var subject: String,
    var body: String,
)