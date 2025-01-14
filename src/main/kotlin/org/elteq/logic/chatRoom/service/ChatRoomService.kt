package org.elteq.logic.chatRoom.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.chatRoom.db.ChatRoom
import org.elteq.logic.chatRoom.models.ChatRoomAddDTO
import org.elteq.logic.chatRoom.models.ChatRoomAddMessageDTO
import org.elteq.logic.chatRoom.spec.ChatRoomSpec

interface ChatRoomService {
    fun add(dto: ChatRoomAddDTO): ChatRoom
    fun getById(id: String): ChatRoom?
    fun addMessage(dto: ChatRoomAddMessageDTO): ChatRoom?
    fun all(spec: ChatRoomSpec): PanacheQuery<ChatRoom>
    fun delete(id: String): String
    fun deleteAll(): String
    fun count(): Long
    fun getByRoomIdAndUserId(roomId: String, userId: String): ChatRoom?

}