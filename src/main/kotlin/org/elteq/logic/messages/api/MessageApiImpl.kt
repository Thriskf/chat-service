package org.elteq.logic.messages.api

import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import org.elteq.base.apiResponse.GenericHttpResponse.Companion.wrapApiResponse
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInSuccessResponse
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.messages.models.Messages
import org.elteq.logic.messages.dtos.MessageAddDTO
import org.elteq.logic.messages.dtos.MessageDTO
import org.elteq.logic.messages.service.MessageService
import org.elteq.logic.messages.spec.MessageSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.Collectors

class MessageApiImpl(@Inject var service: MessageService) : MessageApi {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper

    override fun add(dto: MessageAddDTO): ApiResponse<MessageDTO> {
        logger.info("adding new message: $dto")
        try {
            val ent = service.add(dto)
            val dto = modelMapper.map(ent, MessageDTO::class.java)
            val response = wrapInSuccessResponse(dto)
            logger.info("added new message response: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to add new message", e)
            return wrapFailureInResponse("Could not add new message")
        }
    }

    override fun getById(id: String): ApiResponse<MessageDTO> {
        logger.info("getting message with id $id")
        try {
            val ent = service.getById(id)
            val dto = modelMapper.map(ent, MessageDTO::class.java)
            val response = wrapInSuccessResponse(dto)
            logger.info("get message response :: $response")
            return response
        } catch (e: NoSuchElementException) {
            logger.error("Could not find message with id: $id", e)
            return wrapFailureInResponse("Could not find message with id: $id")
        } catch (e: Exception) {
            logger.error("Failed to get message with id: $id", e)
            return wrapFailureInResponse("Could not get message with id: $id")
        }

    }

    override fun search(spec: MessageSpec): ApiResponse<List<MessageDTO>> {
        logger.info("searching messages with spec: $spec")
        try {
            val ents = service.all(spec)
            val dtos = ents.stream<Messages>().map {
                modelMapper.map(it, MessageDTO::class.java)
            }.collect(Collectors.toList())
            logger.info("mapped message :: $dtos")
            val response = wrapInSuccessResponse(dtos)
            logger.info("filter message response :: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to filter message with spec: $spec", e)
            return wrapFailureInResponse("Could not filter message with spec: $spec")
        }
    }

    override fun getByUser(userId: String): ApiResponse<List<MessageDTO>> {
        logger.info("getting messages with user id $userId")
        try {
            val ents = service.getByUser(userId)
            val dtos = ents.stream<Messages>().map {
                modelMapper.map(it, MessageDTO::class.java)
            }.collect(Collectors.toList())

            logger.info("mapped message :: $dtos")
            val response = wrapInSuccessResponse(dtos)
            logger.info("get message by user response :: $response")
            return response
        } catch (e: NoSuchElementException) {
            logger.error("Could not find message with user id: $userId", e)
            return wrapFailureInResponse("Could not find message with user id: $userId")
        } catch (e: Exception) {
            logger.error("Failed to get message by user id: $userId", e)
            return wrapFailureInResponse("Could not get message by user id: $userId")
        }

    }

    override fun delete(id: String): Response {
        logger.info("Deleting message with id: $id")
        try {
            val ent = service.delete(id)
            val response = wrapApiResponse(ent)
            logger.info("Deleted $response message.")
            return response
        } catch (e: NoSuchElementException) {
            logger.error("Could not delete message with id: $id", e)
            throw ServiceException(-1, "Could not find message with id: $id")
        } catch (e: Exception) {
            logger.error("Failed to delete message with id: $id", e)
            throw ServiceException(-2, "Could not delete message with id: $id")
        }

    }

    override fun updateStatus(dto: Any): ApiResponse<MessageDTO> {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): Response {
        logger.info("Deleting filter messages.")
        try {
            val ent = service.deleteAll()
            val response = wrapApiResponse(ent)
            logger.info("Deleted $response messages.")
            return response
        } catch (e: Exception) {
            logger.error("Failed to delete filter messages.", e)
            throw ServiceException(-2, "Could not delete filter messages.")
        }

    }

    override fun count(): Response {
        logger.info("Counting messages.")
        try {
            val count = service.count()
            val response = wrapApiResponse(count)
            logger.info("Counted $response messages.")
            return response
        } catch (e: Exception) {
            logger.error("could not count messages")
            throw ServiceException(-3, "Could not get count of messages")
        }

    }
}