package org.elteq.logic.users.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.logic.users.enums.Status
import java.util.*

@Serializable
data class UserUpdateStatusDTO(
    @NotBlank
    var id: UUID? = null,
    @NotBlank
    var status: Status? = null,


)