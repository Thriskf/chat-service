package org.elteq.logic.messages.ws

import jakarta.websocket.Session
import jakarta.websocket.server.PathParam

interface ChatMessage {
    fun onOpen(session: Session, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String)
    fun onClose(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String)
    fun onError(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String, throwable: Throwable)
    fun onMessage(message: String, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String)
}