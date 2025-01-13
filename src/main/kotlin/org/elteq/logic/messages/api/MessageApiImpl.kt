package org.elteq.logic.messages.api

import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.messages.spec.MessageSpec
import java.util.*

@Transactional
class MessageApiImpl: MessageApi {
    override fun add(dto: Any): ApiResponse<Any> {
        TODO("Not yet implemented")
    }

    override fun getById(id: UUID): ApiResponse<Any> {
        TODO("Not yet implemented")
    }

    override fun search(spec: MessageSpec): ApiResponse<List<Any>> {
        TODO("Not yet implemented")
    }

    override fun addInCharge(dto: Any): ApiResponse<Any> {
        TODO("Not yet implemented")
    }

    override fun getByContact(email: String): Response {
        TODO("Not yet implemented")
    }

    override fun delete(wardId: UUID): Response {
        TODO("Not yet implemented")
    }

    override fun updateStatus(dto: Any): ApiResponse<Any> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Response {
        TODO("Not yet implemented")
    }

    override fun count(): Response {
        TODO("Not yet implemented")
    }
}