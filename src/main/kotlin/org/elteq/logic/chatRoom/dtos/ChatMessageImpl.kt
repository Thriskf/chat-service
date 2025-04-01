package org.elteq.logic.chatRoom.dtos

import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.websocket.*
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import org.elteq.logic.chatRoom.service.ChatRoomService
import org.elteq.logic.messages.service.MessageService
import org.elteq.logic.messages.ws.ChatMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

@ApplicationScoped
@ServerEndpoint("/chat/{roomId}/{userId}")
//@Transactional
//@Blocking
class ChatMessageImpl : ChatMessage {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var sessions: MutableMap<String, Session> = ConcurrentHashMap()

    @Inject
    @field:Default
    private lateinit var chatRoomService: ChatRoomService

    @Inject
    @field:Default
    private lateinit var messageService: MessageService

    @OnOpen
    override fun onOpen(session: Session, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String) {
        logger.info("user $userId wants to join chat $roomId")

        logger.info("getting chat room with id $roomId and user with id $userId")
//        val room = chatRoomService.getByRoomIdAndUserId(userID, roomID)
//        logger.info("got chat room with id $roomID and user with id $userID\nCHATROOM :: $room")
//
//        if (room == null) {
//            logger.info("user $userId can't join chat $roomId")
//            session.close(
//                CloseReason(
//                    CloseReason.CloseCodes.CANNOT_ACCEPT,
//                    "User $userId can't join chat $roomId"
//                )
//            )
//            return
//        }
//
//        broadcast("User $userId joined")
//        sessions[userId] = session
//        logger.info("user $userId joined chat $roomId")

        Uni.createFrom().item {
            chatRoomService.getByRoomIdAndUserId(roomId, userId)// Blocking operation
        }.runSubscriptionOn(Infrastructure.getDefaultExecutor())
            .subscribe().with({ room ->
                logger.info("got CHATROOM :: $room")
                if (room == null) {
                    logger.info("chat room is null")
                    session.close(
                        CloseReason(
                            CloseReason.CloseCodes.CANNOT_ACCEPT,
                            "User $userId can't join chat $roomId"
                        )
                    )
                } else {
                    logger.info("user $userId can join chat $roomId")
                    broadcast("User $userId joined")
                    sessions[userId] = session
                    logger.info("user $userId joined chat $roomId")
                }
            }, { throwable ->
                logger.error("Error retrieving room", throwable)
                session.close(
                    CloseReason(
                        CloseReason.CloseCodes.UNEXPECTED_CONDITION,
                        "Internal server error"
                    )
                )
            })
    }

    @OnClose
    override fun onClose(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String) {
        logger.info("user $userId closed connection in chat $roomId")
        sessions.remove(userId)
        broadcast("User $userId left")
    }

    @OnError
    override fun onError(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String, throwable: Throwable) {
        logger.error("user $userId error in chat $roomId", throwable)
        sessions.remove(userId)
        broadcast("User $userId left on error: $throwable")
    }

    @OnMessage
    override fun onMessage(message: String, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String) {
        logger.info("received message:$message from user: $userId in chat:: $roomId")
        broadcast("$userId: $message")

        CompletableFuture.runAsync {
//            val userID = UUID.fromString(userId)
//            val roomID = UUID.fromString(roomId)
            val msg = messageService.add(message, roomId, userId)
            logger.info("persisted message: $msg")
        }
    }

    private fun broadcast(message: String) {
        sessions.values.forEach(Consumer { s: Session ->
            s.asyncRemote.sendObject(
                message
            ) { result: SendResult ->
                if (result.exception != null) {
                    logger.error("Unable to send message::", result.exception)
                }
            }
        })
    }

}