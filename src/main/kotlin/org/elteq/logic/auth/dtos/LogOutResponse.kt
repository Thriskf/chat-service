package org.elteq.logic.auth.dtos

data class LogOutResponse(
    val message: String,
    var status: Boolean? = null
)