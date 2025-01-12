package org.elteq.base.exception.handler

import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage.FAIL
import org.slf4j.LoggerFactory

@Provider
class ConstraintExceptionHandler : ExceptionMapper<ConstraintViolationException> {
    private val logger = LoggerFactory.getLogger(ConstraintExceptionHandler::class.java)

    override fun toResponse(ex: ConstraintViolationException): Response {

        val errorResponse = ErrorResponse(
            errorCode = Response.Status.BAD_REQUEST.statusCode,
            errorMessage = getResponseMessage(ex.message),
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = FAIL.code,
            message = FAIL.message,
            data = null,
            error = errorResponse
        )

        logger.error("[ HTTP ERROR:ConstraintExceptionHandler {}", ex.stackTraceToString())

        return Response.status(Response.Status.BAD_REQUEST).entity(apiResponse).build()
    }

    private fun getResponseMessage(exceptionMessage: String?): String {

        if (exceptionMessage?.contains("Duplicate") == true) {
            return "Duplicate record"
        }

        return "Constraint exception"

    }
}