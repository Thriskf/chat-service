package org.elteq.logic.chatRoom.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.chatRoom.db.ChatRoom
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.models.*
import org.elteq.logic.chatRoom.spec.ChatRoomSpec

interface ChatRoomService {
    fun add(dto: ChatRoomAddDTO): ChatRoom
    fun getById(id: String): ChatRoom?
    fun addMessage(dto: ChatRoomAddMessageDTO): ChatRoom?
    fun filter(spec: ChatRoomSpec): PanacheQuery<ChatRoom>
    fun delete(id: String): String
    fun deleteAll(): String
    fun count(): Long
    fun getByRoomIdAndUserId(roomId: String, userId: String): ChatRoom?
    fun getByUserId(userId: String): PanacheQuery<ChatRoom>
    fun getByName(name: String): PanacheQuery<ChatRoom>
    fun getByType(type: ChatRoomType): PanacheQuery<ChatRoom>
    fun addGroupMember(dto: ChatRoomAddMemberDTO): ChatRoom
    fun removeGroupMember(dto: ChatRoomRMMemberDTO): ChatRoom
    fun changeGroupName(dto: ChatRoomChangeNameDTO): ChatRoom

}