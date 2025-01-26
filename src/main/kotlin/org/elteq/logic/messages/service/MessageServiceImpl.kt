package org.elteq.logic.messages.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.logic.chatRoom.service.ChatRoomService
import org.elteq.logic.messages.db.MessageRepository
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.messages.models.MessageAddDTO
import org.elteq.logic.messages.models.MessageUpdateDTO
import org.elteq.logic.messages.spec.MessageSpec
import org.elteq.logic.users.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ApplicationScoped
@Transactional
class MessageServiceImpl(@Inject var repo: MessageRepository) : MessageService {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Inject
    @field:Default
    private lateinit var chatRoomService: ChatRoomService

    @Inject
    @field:Default
    private lateinit var userService: UserService

    override fun add(dto: MessageAddDTO): Messages {
        logger.info("Adding new message: $dto")
        val ent = Messages().apply {
            message = dto.message
        }
        repo.persist(ent)
        logger.info("New message with id ${ent.id} added.")
        return ent
    }

    override fun add(msg: String, roomId: String, userId: String): Messages {
        logger.info("Adding new message: $msg with roomId: $roomId")
        val room = chatRoomService.getById(roomId)
        val sender = userService.getById(userId)
        val ent = Messages().apply {
            message = msg
            chatRoom = room
            user = sender
        }

        logger.info("Attaching message to sender...")
        sender.messages?.add(ent)

        logger.info("Attaching message to room...")
        room?.messages?.add(ent)

        logger.info("Persisting new message...")
        repo.persist(ent)
        logger.info("New message with id ${ent.id} added.. ")
        return ent
    }

    override fun getById(id: String): Messages? {
        logger.info("Getting message with id: $id")
//        return repo.findById(id) ?: throw NoSuchElementException("Message with id '$id' not found.")
        return repo.findById(id)
    }

    override fun update(dto: MessageUpdateDTO): Messages {
        TODO("Not yet implemented")
    }

    override fun all(spec: MessageSpec): PanacheQuery<Messages> {
        logger.info("filtering messages with filter: $spec")
        return repo.all(spec)
    }

    override fun getByUser(userId: String): PanacheQuery<Messages> {
        logger.info("Getting messages for user with id: $userId")
        return repo.findByUserId(userId)
    }


    override fun delete(id: String): String {
        logger.info("Deleting message with id: $id")
        val ent = getById(id)
        repo.delete(ent)
        logger.info("Message with id '$id' deleted.")
        return "Message with id '$id' deleted."
    }

    override fun deleteAll(): String {
        logger.info("Deleting filter messages.")
        val res = repo.deleteAll()
        logger.info("Deleted $res messages.")
        return "Deleted $res messages."

    }

    override fun count(): Long {
        logger.info("Counting messages.")
        return repo.count()
    }

    override fun updateStatus(dto: Any): Messages {
        TODO("Not yet implemented")
    }
}