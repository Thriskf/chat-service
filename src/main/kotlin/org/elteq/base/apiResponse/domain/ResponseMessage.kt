package org.elteq.base.apiResponse.domain

enum class ResponseMessage(val code: String, val message: String) {
    SUCCESS("00", "Success"),
    FAIL("01", "Failed"),
    PENDING("03", "Pending"),
}