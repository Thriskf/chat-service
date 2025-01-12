package org.elteq.base.apiResponse

import jakarta.ws.rs.core.Response

class GenericHttpResponse<T>(private var data: T?) {

    private fun isSuccess(): Boolean {
        return data != null
    }

    private fun getMessage(): String {
        return if (isSuccess()) "Success" else "Failed"
    }

    private fun getStatusCode(): Int {
        return if (isSuccess()) Response.Status.OK.statusCode else Response.Status.BAD_REQUEST.statusCode
    }

    private fun toResponse(): Response {
        val status = if (isSuccess()) 200 else 500 // Default to 200 for success, 500 for failure
        val message = getMessage()
        val statusCode = getStatusCode()
        val code = 0
        val error = if (!isSuccess()) Response.serverError() else null

        val responseEntity = mapOf("code" to statusCode, "message" to message, "data" to data, "error" to error)
        return Response.status(statusCode).entity(responseEntity).build()
    }

    companion object {
        fun <T> wrapApiResponse(data: T?): Response {
            val genericResponse = GenericHttpResponse(data)
            return genericResponse.toResponse()
        }
    }
}



