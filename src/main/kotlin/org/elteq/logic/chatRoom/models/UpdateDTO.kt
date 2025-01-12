package org.elteq.logic.chatRoom.models

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import kotlinx.serialization.Serializable
import org.elteq.base.validators.ValidPhoneNumber
import java.util.*

@Serializable
data class UpdateDTO (
    @NotBlank
    var id: UUID? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var otherName: String? = null,

    @Email
    var email: String? = null,

    @ValidPhoneNumber
    var phoneNumber: String? = null,

    )