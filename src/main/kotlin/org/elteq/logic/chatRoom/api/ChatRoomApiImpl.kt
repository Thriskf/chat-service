package org.elteq.logic.chatRoom.api

import io.quarkus.hibernate.orm.panache.PanacheEntity_.id
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.elteq.base.apiResponse.GenericHttpResponse.Companion.wrapApiResponse
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInApiResponse
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.chatRoom.db.ChatRoom
import org.elteq.logic.chatRoom.models.ChatRoomAddDTO
import org.elteq.logic.chatRoom.models.ChatRoomDTO
import org.elteq.logic.chatRoom.service.ChatRoomService
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@Transactional
class ChatRoomApiImpl(@Inject var service: ChatRoomService) : ChatRoomApi {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper

    override fun add(dto: ChatRoomAddDTO): ApiResponse<ChatRoomDTO> {
        logger.info("add chat room with payload: $dto")
        try {
            val ent = service.add(dto)
            val dto = modelMapper.map(ent, ChatRoomDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("added chat room with payload: $response")
            return response
        } catch (e: Exception) {
            logger.error("Failed to add chat room:", e)
            return wrapFailureInResponse("Could not add chat room")
        }
    }

    override fun getById(id: UUID): ApiResponse<ChatRoomDTO> {
        logger.info("get chat room with id: $id")
        try {
            val ent = service.getById(id)
            val dto = modelMapper.map(ent, ChatRoomDTO::class.java)
            val response = wrapInApiResponse(dto)
            logger.info("get chat room with id: $response")
            return response
        } catch (e: NoSuchElementException) {
            logger.error("could not get chatroom with id: $id", e)
            return wrapFailureInResponse("No chat room found with id: $id")
        } catch (e: Exception) {
            logger.error("could not get chatroom with id: $id", e)
            return wrapFailureInResponse("No chat room found with id: $id")
        }
    }

    override fun search(spec: ChatRoomSpec): ApiResponse<List<ChatRoomDTO>> {
        logger.info("filter chatroom with $spec")
        try {
            val ents = service.all(spec)
            val dtos = ents.stream<ChatRoom>().map {
                modelMapper.map(it, ChatRoomDTO::class.java)
            }
            val response = wrapInApiResponse(dtos.toList())
            logger.info("filtered chatroom with $spec: $response")
            return response

        } catch (e: NoSuchElementException) {
            logger.error("could not filter chatroom", e)
            return wrapFailureInResponse("No chat room found with id: $id")
        } catch (e: Exception) {
            logger.error("Failed to filter chatrooms:", e)
            return wrapFailureInResponse("Could not get chatrooms")
        }
    }


    override fun delete(id: UUID): ApiResponse<String> {
        logger.info("delete chatroom with id: $id")
        try {
            val ent = service.delete(id)
            val response = wrapInApiResponse(ent)
            logger.info("deleted chatroom with id: $response")
            return response
        } catch (e: NoSuchElementException) {
            logger.error("could not delete chatroom with id: $id", e)
            return wrapFailureInResponse("No chat room found with id: $id")
        } catch (e: Exception) {
            logger.error("could not delete chatroom with id: $id", e)
            return wrapFailureInResponse("Could not delete chatroom with id: $id")
        }
    }


    override fun deleteAll(): ApiResponse<String> {
        logger.info("delete all chatrooms")
        try {
            val ent = service.deleteAll()
            val response = wrapInApiResponse(ent)
            logger.info("deleted all chatrooms: $response")
            return response
        } catch (e: Exception) {
            logger.error("could not delete all chatrooms", e)
            return wrapFailureInResponse("Could not delete all chatrooms")
        }
    }

    override fun count(): Response {
        logger.info("count chat rooms.")
        try {
            val count = service.count()
            val response = wrapApiResponse(count)
            logger.info("counted chat rooms: $response")
            return response
        } catch (e: Exception) {
            logger.error("could not get count of chat rooms")
            throw ServiceException(-2, "Could not get count of chat rooms")
        }
    }
}