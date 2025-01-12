package org.elteq.base.exception.handler

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.elteq.base.exception.ServiceException
import org.slf4j.LoggerFactory

@Provider
class ServiceExceptionHandler : ExceptionMapper<ServiceException> {
    private val logger = LoggerFactory.getLogger(ServiceExceptionHandler::class.java)

    override fun toResponse(ex: ServiceException): Response {

        val errorResponse = ErrorResponse(
            errorCode = ex.code,
            errorMessage = ex.message,
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = ResponseMessage.FAIL.code,
            message = ResponseMessage.FAIL.message,
            data = null,
            error = errorResponse
        )

        logger.error("[ HTTP ERROR:ServiceExceptionHandler {}", ex.stackTraceToString())

        return Response.status(Response.Status.OK).entity(apiResponse).build()
    }
}