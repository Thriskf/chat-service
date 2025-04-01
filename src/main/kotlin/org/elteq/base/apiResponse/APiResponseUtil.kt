package org.elteq.base.apiResponse

import io.quarkus.panache.common.Page
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.PagedContent
import org.elteq.base.apiResponse.domain.ResponseMessage.FAIL
import org.elteq.base.apiResponse.domain.ResponseMessage.SUCCESS


fun <T> wrapInSuccessResponse(data: T): ApiResponse<T> {
    return ApiResponse(
        code = SUCCESS.code,
        message = SUCCESS.message,
        data = listOf(data)
    )
}

fun <T> wrapFailureInResponse(message: String): ApiResponse<T> {
    return ApiResponse(
        code = FAIL.code,
        message = "Error",
        data = listOf(),
        error = ErrorResponse(errorCode = -1, errorMessage = message)
    )
}

//fun <T> wrapFailureInResponse(message: String): ApiResponse<List<T>> {
//    return ApiResponse(
//        code = 0,
//        message = "Error",
//        data = listOf(),
//        error = ErrorResponse(errorCode = -1, errorMessage = message)
//    )
//}

//fun <T, W> wrapInPagedApiResponse(page: Page, data: List<W>?): ApiResponse<PagedContent<W>> {
//    return ApiResponse(
//        code = SUCCESS.code,
//        message = SUCCESS.message,
//        systemMessage = "",
//        systemCode = "",
//        data = PagedContent(page, data)
//    )
//}