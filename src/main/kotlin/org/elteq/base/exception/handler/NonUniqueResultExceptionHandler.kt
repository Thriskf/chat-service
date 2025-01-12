package org.elteq.base.exception.handler


import jakarta.persistence.NonUniqueResultException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.slf4j.LoggerFactory


@Provider
class NonUniqueResultExceptionHandler : ExceptionMapper<NonUniqueResultException> {
    private val logger = LoggerFactory.getLogger(NonUniqueResultExceptionHandler::class.java)

    override fun toResponse(ex: NonUniqueResultException): Response {

        val errorResponse = ErrorResponse(
            errorCode = Response.Status.INTERNAL_SERVER_ERROR.statusCode,
            errorMessage = "Internal server error",
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = ResponseMessage.FAIL.code,
            message = ResponseMessage.FAIL.message,
            data = null,
            error = errorResponse
        )

        logger.error("[ HTTP ERROR:NonUniqueResultExceptionHandler {}", ex.stackTraceToString())

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(apiResponse).build()
    }
}