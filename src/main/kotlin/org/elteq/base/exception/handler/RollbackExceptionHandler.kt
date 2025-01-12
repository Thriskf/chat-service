package org.elteq.base.exception.handler

import jakarta.transaction.RollbackException
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.domain.ErrorResponse
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory

@Provider
class RollbackExceptionHandler : ExceptionMapper<RollbackException> {
    private val logger = LoggerFactory.getLogger(RollbackExceptionHandler::class.java)

    override fun toResponse(ex: RollbackException): Response {
        if (ex.cause is ConstraintViolationException) {
            return constraintViolationExceptionHandler(ex.cause as ConstraintViolationException)
        }

        return genericRollbackException(ex)
    }

    private fun constraintViolationExceptionHandler(ex: ConstraintViolationException): Response {

        val errorResponse = ErrorResponse(
            errorCode = Response.Status.CONFLICT.statusCode,
            errorMessage = getDBConstraintResponseMessage(ex.localizedMessage),
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = ResponseMessage.FAIL.code, message = ResponseMessage.FAIL.message, data = null, error = errorResponse
        )

        logger.error("[ HTTP ERROR:ConstraintViolationException {}", ex.stackTraceToString())

        return Response.status(Response.Status.CONFLICT).entity(apiResponse).build()
    }

    private fun genericRollbackException(ex: RollbackException): Response {
        val errorResponse = ErrorResponse(
            errorCode = 422,
            errorMessage = ex.message,
            url = "n/a",
        )

        val apiResponse: ApiResponse<String> = ApiResponse(
            code = ResponseMessage.FAIL.code, message = ResponseMessage.FAIL.message, data = null, error = errorResponse
        )

        logger.error("[ HTTP ERROR:RollbackExceptionHandler {}", ex.stackTraceToString())

        return Response.status(422).entity(apiResponse).build()
    }

    private fun getDBConstraintResponseMessage(exceptionMessage: String?): String {
        if (exceptionMessage?.contains("Duplicate") == true) {
            return "Duplicate record"
        }

        return "Constraint rollback exception"
    }
}