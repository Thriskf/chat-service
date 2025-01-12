package org.elteq.base.exception.handler

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.slf4j.LoggerFactory


@Provider
class NotFoundExceptionHandler : ExceptionMapper<jakarta.ws.rs.NotFoundException> {
    private val logger = LoggerFactory.getLogger(NotFoundExceptionHandler::class.java)

    override fun toResponse(ex: jakarta.ws.rs.NotFoundException): Response {

        val errorResponse = ErrorResponse(
            errorCode = Response.Status.NOT_FOUND.statusCode,
            errorMessage = ex.message,
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = ResponseMessage.FAIL.code,
            message = ResponseMessage.FAIL.message,
            data = null,
            error = errorResponse
        )

        logger.error("[ HTTP ERROR:NotFoundExceptionHandler {}", ex.stackTraceToString())

        return Response.status(Response.Status.NOT_FOUND).entity(apiResponse).build()
    }
}