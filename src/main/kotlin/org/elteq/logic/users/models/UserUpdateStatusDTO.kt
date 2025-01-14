package org.elteq.logic.users.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.logic.users.enums.Status

@Serializable
data class UserUpdateStatusDTO(
    @NotBlank
    var id: String? = null,
    @NotBlank
    var status: Status? = null,


)