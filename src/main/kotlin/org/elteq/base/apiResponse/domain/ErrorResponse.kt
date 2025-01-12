package org.elteq.base.apiResponse.domain

data class ErrorResponse(
    val errorCode: Int,
    var errorMessage: String?,
    var url: String? = "",
)