package org.elteq.logic.chatRoom.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.chatRoom.dtos.*
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.models.ChatRoom
import org.elteq.logic.chatRoom.models.ChatRoomRepository
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.users.db.Users
import org.elteq.logic.users.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

@ApplicationScoped
@Transactional
class ChatRoomServiceImpl(@Inject var repo: ChatRoomRepository) : ChatRoomService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Inject
    @field:Default
    lateinit var userService: UserService

    private val modelMapper = Mapper.mapper


    override fun add(dto: ChatRoomAddDTO): ChatRoomResponse {

        val data = mutableListOf<ChatRoomDTO>()
        var message = ResponseMessage.SUCCESS.message
        var code = ResponseMessage.SUCCESS.code
        var systemMessage = "Chat Room added successfully"
        val systemCode = ResponseMessage.SUCCESS.code


        val ent = ChatRoom()
        val users = mutableSetOf<Users>()

        val memberSize = dto.phoneNumbers?.size!!

        if (dto.name.isNullOrEmpty() && memberSize == 2) {
            ent.type = ChatRoomType.DM
        }

        if (!dto.name.isNullOrEmpty() && memberSize >= 2) {
            ent.type = ChatRoomType.GROUP
            ent.name = dto.name
        }

        if (dto.name.isNullOrEmpty() && memberSize > 2) {
            ent.type = ChatRoomType.BROADCAST
        }

        if (memberSize == 1) {
            ent.type = ChatRoomType.SELF
        }

        dto.phoneNumbers?.parallelStream()?.forEach {
            logger.info("phone number of user to add $it")

            val user = userService.getByContact(it)
            user.chatRooms?.add(ent)
            users.add(user)

        }
        ent.users = users

        runCatching {
            repo.persist(ent)
            logger.info("New chat room with $ent created.")
            modelMapper.map(ent, ChatRoomDTO::class.java)
        }.fold(
            onSuccess = { dto ->

//                val response = wrapInSuccessResponse(dto)
                data.add(dto)
                logger.info("successfully added chatroom ${ent.name}")
//                response
            },
            onFailure = {
                message = ResponseMessage.FAIL.message
                code = ResponseMessage.FAIL.code
                systemMessage = "Could not add chat room ${ent.name}"
                logger.error("Error adding chatroom", it)
//                wrapFailureInResponse("Could not add chat room")
            })
        val response = ChatRoomResponse(data)
        response.code = code
        response.systemCode = systemCode
        response.message = systemMessage
        response.message = message
        return response
    }

    override fun getById(id: String): ChatRoom? {
        return repo.findById(id) ?: throw ServiceException("No room found with id '$id'")
    }

    override fun addMessage(dto: ChatRoomAddMessageDTO): ChatRoom? {
        val ent = getById(dto.id!!)

        val msg = Messages().apply {
            message = dto.message!!
        }

        ent?.messages?.add(msg)
        repo.entityManager.merge(ent)
        logger.info("Message '${msg.message}' added to chat room with id '${ent?.id}'")
        return ent
    }


    override fun filter(spec: ChatRoomSpec): ChatRoomPaginatedResponse {

        val data = mutableListOf<ChatRoomDTO>()
        var message = ResponseMessage.SUCCESS.message
        var code = ResponseMessage.SUCCESS.code
        var systemMessage = "Chat room filtered successfully"
        val systemCode = ResponseMessage.SUCCESS.code
        var query: PanacheQuery<ChatRoom>? = null

        logger.info("filtering chat rooms with filter: $spec")
        runCatching {
            query = repo.filter(spec)
        }.fold(onSuccess = {
            query?.stream<ChatRoom>()?.map { room ->
                modelMapper.map(room, ChatRoomDTO::class.java)
            }?.collect(Collectors.toList())?.parallelStream()?.forEach { dto ->
                data.add(dto)
            }

        }, onFailure = {
            logger.error("Could not filter chat room", it)
            message = ResponseMessage.FAIL.message
            code = ResponseMessage.FAIL.code
            systemMessage = "Could chatRooms}"
        })


        val response = ChatRoomPaginatedResponse(data)
        response.code = code
        response.systemCode = systemCode
        response.message = message
        response.systemMessage = systemMessage
        response.page = spec.page
        response.totalCount = query!!.count()
        response.pageSize = query!!.pageCount()
        return response
    }

    override fun delete(id: String): String {
        val ent = getById(id)
        repo.delete(ent)
        return "Chat room with id '$id' deleted."
    }

    override fun deleteAll(): String {
        val res = repo.deleteAll()
        return "Deleted $res chat rooms."
    }

    override fun count(): Long {
        return repo.count()
    }

    override fun getByRoomIdAndUserId(roomId: String, userId: String): ChatRoom? {
        logger.info("about to get chat room by roomId: $roomId and userId: $userId")
//        return repo.findByRoomIdAndUserId(roomId, userId) ?: throw NoSuchElementException("No chat room found")
        return repo.findByRoomIdAndUserId(roomId, userId)
    }

    override fun getByName(name: String): PanacheQuery<ChatRoom> {
        logger.info("about to get chat room by name: $name")
        return repo.findByName(name)
    }

    override fun getByType(type: ChatRoomType): PanacheQuery<ChatRoom> {
        logger.info("about to get chat room by type: $type")
        return repo.findByType(type)
    }

    override fun getByUserId(userId: String): PanacheQuery<ChatRoom> {
        logger.info("about to get chat room by userId: $userId")
        return repo.findByUserId(userId)
    }

    override fun addGroupMember(dto: ChatRoomAddMemberDTO): ChatRoom {
        logger.info("adding group member to chat room with payload: $dto")
        val ent = getById(dto.roomId!!)

        if (ent?.type != ChatRoomType.GROUP) {
            throw ServiceException(-2, "This is not a group chat")
        }

        val users = mutableSetOf<Users>()

        dto.phoneNumbers?.parallelStream()?.forEach { contact ->
            val user = userService.getByContact(contact)
            val isUserInGroup = ent.id?.let { user.id?.let { userId -> this.checkUserInGroup(userId, it) } }!!

            if (isUserInGroup) {
                logger.warn("User ${user.id} is already in group. Skipping ....")
            } else {
                user.chatRooms?.add(ent)
                users.add(user)
            }
        }

        ent.users = users
        logger.info("members added to group")
        repo.entityManager.merge(ent)
        return ent
    }

    private fun checkUserInGroup(userId: String, roomId: String): Boolean {
        val room = this.getByRoomIdAndUserId(roomId, userId)
        return room != null
    }

    override fun removeGroupMember(dto: ChatRoomRMMemberDTO): ChatRoom {
        logger.info("removing group member to chat room with payload: $dto")
        val ent = getById(dto.id!!)
        val user = userService.getByContact(dto.phoneNumber!!)

        ent?.users?.remove(user)
        user.chatRooms?.remove(ent)

        logger.info("Group member removed")
        repo.entityManager.merge(ent)
        return ent!!
    }

    override fun changeGroupName(dto: ChatRoomChangeNameDTO): ChatRoom {
        logger.info("changing group member to chat room with payload: $dto")
        val ent = getById(dto.id!!)
        ent?.name = dto.name
        repo.entityManager.merge(ent)
        logger.info("group name updated")
        return ent!!
    }
}