package org.elteq.logic.chatRoom.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.base.exception.ServiceException
import org.elteq.logic.chatRoom.db.ChatRoom
import org.elteq.logic.chatRoom.db.ChatRoomRepository
import org.elteq.logic.chatRoom.models.ChatRoomAddDTO
import org.elteq.logic.chatRoom.models.ChatRoomAddMessageDTO
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.users.db.Users
import org.elteq.logic.users.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

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

        dto.phoneNumbers?.parallelStream()?.forEach {
            val user = userService.getByContact(it)
            user.chatRoom = ent
            users.add(user)
        }
        ent.users = users

        repo.persist(ent)
        logger.info("New chat room with $ent created.")
        return ent
    }

    override fun getById(id: UUID): ChatRoom? {
        return repo.findById(id) ?:throw ServiceException("No user found with id '$id'")
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


    override fun all(spec: ChatRoomSpec): PanacheQuery<ChatRoom> {
        logger.info("filtering chat rooms with filter: $spec")
        return repo.all(spec)
    }

    override fun delete(id: UUID): String {
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

    override fun getByRoomIdAndUserId(roomId: UUID, userId: UUID): ChatRoom? {
        logger.error("about to get chat room by roomId: $roomId and userId: $userId")
//        return repo.findByRoomIdAndUserId(roomId, userId) ?: throw NoSuchElementException("No chat room found")
        return repo.findByRoomIdAndUserId(roomId, userId)
    }

}