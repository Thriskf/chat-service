package org.elteq.logic.chatRoom.api

import io.quarkus.hibernate.orm.panache.PanacheEntity_.id
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.ws.rs.core.Response
import org.elteq.base.apiResponse.GenericHttpResponse.Companion.wrapApiResponse
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.base.apiResponse.wrapFailureInResponse
import org.elteq.base.apiResponse.wrapInSuccessResponse
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.chatRoom.dtos.*
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.models.ChatRoom
import org.elteq.logic.chatRoom.service.ChatRoomService
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

@Transactional
class ChatRoomApiImpl(@Inject var service: ChatRoomService) : ChatRoomApi {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper

    override fun add(dto: ChatRoomAddDTO): ChatRoomResponse {
        logger.info("add chat room with payload: $dto")
        return service.add(dto)
    }

    override fun getById(id: String): ApiResponse<ChatRoomDTO> {
        logger.info("get chat room with id: $id")
        try {
            val ent = service.getById(id)
            val dto = modelMapper.map(ent, ChatRoomDTO::class.java)
            val response = wrapInSuccessResponse(dto)
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

    override fun search(spec: ChatRoomSpec): ChatRoomPaginatedResponse {
        logger.info("filter chatroom with $spec")
        return service.filter(spec)

    }

    override fun delete(id: String): ApiResponse<String> {
        logger.info("delete chatroom with id: $id")
        try {
            val ent = service.delete(id)
            val response = wrapInSuccessResponse(ent)
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
        logger.info("delete filter chatrooms")
        try {
            val ent = service.deleteAll()
            val response = wrapInSuccessResponse(ent)
            logger.info("deleted filter chatrooms: $response")
            return response
        } catch (e: Exception) {
            logger.error("could not delete filter chatrooms", e)
            return wrapFailureInResponse("Could not delete filter chatrooms")
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

    override fun getByUserId(userId: String): ApiResponse<List<ChatRoomDTO>> {
        logger.info("get chat rooms for $userId")
        return try {
            val ents = service.getByUserId(userId)
            val dtos = ents.stream<ChatRoom>().map {
                modelMapper.map(it, ChatRoomDTO::class.java)
            }.collect(Collectors.toList())
            logger.info("get by user id dtos : $dtos")
            val response = wrapInSuccessResponse(dtos)
            logger.info("get by user id dtos: $response")
            response

        } catch (e: Exception) {
            logger.error("could not get chatroom by user id", e)
            wrapFailureInResponse("Could not get chat room")
        }
    }

    override fun getByName(name: String): ApiResponse<List<ChatRoomDTO>> {
        logger.info("get chat rooms for $name")
        try {
            val ents = service.getByName(name)
            val dtos = ents.stream<ChatRoom>().map {
                modelMapper.map(it, ChatRoomDTO::class.java)
            }.collect(Collectors.toList())

            logger.info("get chatroom by name dto: $dtos")
            val response = wrapInSuccessResponse(dtos)
            logger.info("get chat room by name dto: $response")
            return response
        } catch (e: Exception) {
            logger.error("could not get chat room by name", e)
            return wrapFailureInResponse("Could not get chat room")
        }
    }

    override fun getByType(type: ChatRoomType): ApiResponse<List<ChatRoomDTO>> {
        logger.info("get chat rooms for $type")
        return try {
            val ents = service.getByType(type)
            val dtos = ents.stream<ChatRoom>().map {
                modelMapper.map(it, ChatRoomDTO::class.java)
            }.collect(Collectors.toList())
            logger.info("GET by type dtos :: $dtos")
            val response = wrapInSuccessResponse(dtos)
            logger.info("get chat room by type dtos :: $response")
            response
        } catch (e: Exception) {
            logger.error("Could not get chatrooms by type", e)
            wrapFailureInResponse("Could not get chatrooms")
        }
    }

    override fun addGroupMember(dto: ChatRoomAddMemberDTO): ApiResponse<ChatRoomDTO> {
        logger.info("add group member with payload $dto")
        return try {
            val ent = service.addGroupMember(dto)
            val dto = modelMapper.map(ent, ChatRoomDTO::class.java)
            val response = wrapInSuccessResponse(dto)
            logger.info("add group member response: $response")
            response
        } catch (e: NoSuchElementException) {
            logger.error("NSEE :: could not add member to group", e)
            wrapFailureInResponse("Could not add member to group")
        } catch (e: Exception) {
            logger.error("could not add member to group")
            wrapFailureInResponse("Could not add member to group")
        }
    }

    override fun removeGroupMember(dto: ChatRoomAddMemberDTO): ApiResponse<ChatRoomDTO> {
        logger.info("remove group member with payload $dto")
        return try {
            val ent = service.removeGroupMember(dto)
            val dto = modelMapper.map(ent, ChatRoomDTO::class.java)
            val response = wrapInSuccessResponse(dto)
            logger.info("remove group member response: $response")
            response
        } catch (e: NoSuchElementException) {
            logger.error("NSEE :: could not remove member from group", e)
            wrapFailureInResponse("Could not remove member from group")
        } catch (e: Exception) {
            logger.error("could not remove member to group")
            wrapFailureInResponse("Could not remove member from group")
        }    }

    override fun changeGroupName(dto: ChatRoomChangeNameDTO): ApiResponse<ChatRoomDTO> {
        logger.info("change group name with payload $dto")
        return try {
            val ent = service.changeGroupName(dto)
            val dto = modelMapper.map(ent, ChatRoomDTO::class.java)
            val response = wrapInSuccessResponse(dto)
            logger.info("change group name response: $response")
            response
        } catch (e: NoSuchElementException) {
            logger.error("NSEE :: could not change group name", e)
            wrapFailureInResponse("Could not group name")
        } catch (e: Exception) {
            logger.error("could not change group name")
            wrapFailureInResponse("Could not change group name")
        }    }
}