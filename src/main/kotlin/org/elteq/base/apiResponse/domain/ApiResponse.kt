package org.elteq.base.apiResponse.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse<T>(
    var code: String,
    @JsonProperty("message")
    var message: String = "",
    @JsonProperty("systemMessage")
    var systemMessage: String = "",
    @JsonProperty("systemCode")
    var systemCode: String = "",
    var data: List<T>?,
    var error: ErrorResponse? = null,

    )

@JsonInclude(JsonInclude.Include.ALWAYS)
open class BaseResponse {
    open var message: String? = "Success"
    open var systemCode: String? = "B000"
    open var systemMessage: String? = "Success"
    open var code: String? = "00"
}

@JsonInclude(JsonInclude.Include.ALWAYS)
open class PaginatedBaseResponse() : BaseResponse() {
    var page: Int? = 0
    var pageSize: Int? = 0
    var totalCount: Long? = 0
}