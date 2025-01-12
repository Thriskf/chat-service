package org.elteq.logic.users.models

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserUpdateNameDTO(
    @NotBlank
    var id: UUID? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,
    var displayName: String? = null
)