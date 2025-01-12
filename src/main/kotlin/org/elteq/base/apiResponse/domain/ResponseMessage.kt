package org.elteq.base.apiResponse.domain

enum class ResponseMessage(val code: Int, val message: String) {
    SUCCESS(0, "Success"),
    FAIL(1, "Failed"),
}