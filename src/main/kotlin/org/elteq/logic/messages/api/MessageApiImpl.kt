package org.elteq.logic.messages.api

import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.messages.service.MessageService
import org.elteq.logic.messages.spec.MessageSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class MessageApiImpl(@Inject var service: MessageService) : MessageApi {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper

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