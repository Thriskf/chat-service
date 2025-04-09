package org.elteq.logic.messages.ws

import io.quarkus.security.Authenticated
import jakarta.websocket.Session
import jakarta.websocket.server.PathParam
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement
import org.eclipse.microprofile.openapi.annotations.tags.Tag

@Authenticated
@Tag(name = "Socket", description = "Socket")
@SecurityRequirement(name = "SecurityScheme")

interface ChatMessage {
    fun onOpen(session: Session, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String)
    fun onClose(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String)
    fun onError(session: Session?, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String, throwable: Throwable)
    fun onMessage(message: String, @PathParam("roomId") roomId: String, @PathParam("userId") userId: String)
}