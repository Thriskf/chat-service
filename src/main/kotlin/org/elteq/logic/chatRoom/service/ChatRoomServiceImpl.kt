package org.elteq.logic.chatRoom.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
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
class ServiceImpl(@Inject var repo: ChatRoomRepository) : ChatRoomService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Inject
    @field:Default
    lateinit var userService: UserService

    override fun add(dto: ChatRoomAddDTO): ChatRoom {
        val ent = ChatRoom()
        val users = mutableSetOf<Users>()

        dto.phoneNumbers?.parallelStream()?.forEach {
            val user = userService.getByContact(it.phoneNumber!!)
            users.add(user)
        }
        ent.user = users

        repo.persist(ent)
        logger.info("New chat room with id ${ent.id} created.")
        return ent
    }

    override fun getById(id: UUID): ChatRoom? {
        return repo.findById(id) ?: throw NoSuchElementException("Chat room with id '$id' not found.")
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

}