package org.elteq.base.apiResponse.domain

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse<T>(
    var code: Int,
    @JsonProperty("message")
    var message: String = "",
    @JsonProperty("systemMessage")
    var systemMessage: String = "",
    @JsonProperty("systemCode")
    var systemCode: String = "",
    var data: List<T>?,
    var error: ErrorResponse? = null,

    )
