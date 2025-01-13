package org.elteq.logic.messages.ws

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.websocket.*
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.logic.messages.service.MessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer

@ApplicationScoped
@ServerEndpoint("/chat/{roomId}/{userId}")
class ChatMessageImpl(@Inject var service: MessageService) : ChatMessage {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val modelMapper = Mapper.mapper
    var sessions: MutableMap<String, Session> = ConcurrentHashMap()

    @OnOpen
    override fun onOpen(session: Session, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String) {
        broadcast("User $userId joined")
        sessions[userId] = session
    }

    @OnClose
    override fun onClose(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String) {
        sessions.remove(userId)
        broadcast("User $userId left")
    }

    @OnError
    override fun onError(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String, throwable: Throwable) {
        sessions.remove(userId)
        broadcast("User $userId left on error: $throwable")
    }

    @OnMessage
    override fun onMessage(message: String, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String) {
        broadcast(">> $userId: $message")
    }

    private fun broadcast(message: String) {
        sessions.values.forEach(Consumer { s: Session ->
            s.asyncRemote.sendObject(
                message
            ) { result: SendResult ->
                if (result.exception != null) {
                    println("Unable to send message: " + result.exception)
                }
            }
        })
    }

}