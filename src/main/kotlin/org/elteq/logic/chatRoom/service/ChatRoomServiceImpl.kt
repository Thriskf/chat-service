package org.elteq.logic.chatRoom.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.base.exception.ServiceException
import org.elteq.logic.chatRoom.db.ChatRoom
import org.elteq.logic.chatRoom.db.ChatRoomRepository
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.models.*
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.users.db.Users
import org.elteq.logic.users.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
@Transactional
class ChatRoomServiceImpl(@Inject var repo: ChatRoomRepository) : ChatRoomService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Inject
    @field:Default
    lateinit var userService: UserService

    override fun add(dto: ChatRoomAddDTO): ChatRoom {
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
            val user = userService.getByContact(it)
            user.chatRooms?.add(ent)
            users.add(user)
        }
        ent.users = users

        repo.persist(ent)
        logger.info("New chat room with $ent created.")
        return ent
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


    override fun filter(spec: ChatRoomSpec): PanacheQuery<ChatRoom> {
        logger.info("filtering chat rooms with filter: $spec")
        return repo.filter(spec)
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
        val ent = getById(dto.id!!)

        if (ent?.type != ChatRoomType.GROUP) {
            throw ServiceException(-2, "This is not a group chat")
        }

        val users = mutableSetOf<Users>()
        dto.phoneNumbers?.parallelStream()?.forEach {
            val user = userService.getByContact(it)
            user.chatRooms?.add(ent)
            users.add(user)
        }
        ent.users = users
        logger.info("members added to group")
        repo.entityManager.merge(ent)
        return ent
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