package org.elteq.base.exception.handler

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory


@Provider
class SQLConstraintExceptionHandler : ExceptionMapper<ConstraintViolationException> {
    private val logger = LoggerFactory.getLogger(SQLConstraintExceptionHandler::class.java)

    override fun toResponse(ex: ConstraintViolationException): Response {
        println(ex.message)

        val errorResponse = ErrorResponse(
            errorCode = Response.Status.CONFLICT.statusCode,
            errorMessage = getResponseMessage(ex.message),
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = ResponseMessage.FAIL.code,
            message = ResponseMessage.FAIL.message,
            data = null,
            error = errorResponse
        )

        logger.error("[ HTTP ERROR:SQLConstraintExceptionHandler {}", ex.stackTraceToString())

        return Response.status(Response.Status.CONFLICT).entity(apiResponse).build()
    }

    private fun getResponseMessage(exceptionMessage: String?): String {

        if (exceptionMessage?.contains("Duplicate") == true) {
            return "Duplicate record"
        }

        return "Constraint sql constraint exception"
    }
}