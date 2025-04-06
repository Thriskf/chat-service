package org.elteq.logic.users.dtos

import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateNameDTO(
    @NotBlank
    var id: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,
    var displayName: String? = null
)