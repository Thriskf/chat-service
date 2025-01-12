package org.elteq.logic.chatRoom.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.chatRoom.db.ChatRoom
import org.elteq.logic.chatRoom.models.ChatRoomAddDTO
import org.elteq.logic.chatRoom.models.ChatRoomAddMessageDTO
import org.elteq.logic.chatRoom.spec.ChatRoomSpec
import java.util.*

interface ChatRoomService {
    fun add(dto: ChatRoomAddDTO): ChatRoom
    fun getById(id: UUID): ChatRoom?
    fun addMessage(dto: ChatRoomAddMessageDTO): ChatRoom?
    fun all(spec: ChatRoomSpec): PanacheQuery<ChatRoom>
    fun delete(id: UUID): String
    fun deleteAll(): String
    fun count(): Long

}