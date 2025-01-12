package org.elteq.base.exception.handler

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage.FAIL
import org.slf4j.LoggerFactory


@Provider
class GeneralExceptionHandler : ExceptionMapper<Exception> {
    private val logger = LoggerFactory.getLogger(GeneralExceptionHandler::class.java)

    override fun toResponse(ex: Exception): Response {

        val errorResponse = ErrorResponse(
            errorCode = Response.Status.INTERNAL_SERVER_ERROR.statusCode,
            errorMessage = ex.message,
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = FAIL.code,
            message = FAIL.message,
            data = null,
            error = errorResponse,
        )

        logger.error("[ HTTP ERROR:GeneralExceptionHandler {}", ex.stackTraceToString())

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponse).build()
    }
}