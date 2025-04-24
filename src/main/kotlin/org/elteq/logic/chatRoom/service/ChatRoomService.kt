package org.elteq.logic.chatRoom.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.base.apiResponse.domain.ApiResponse
import org.elteq.logic.chatRoom.models.ChatRoom
import org.elteq.logic.chatRoom.dtos.*
import org.elteq.logic.chatRoom.enums.ChatRoomType
import org.elteq.logic.chatRoom.spec.ChatRoomSpec

interface ChatRoomService {
    fun add(dto: ChatRoomAddDTO): ChatRoomResponse
    fun getById(id: String): ChatRoom?
    fun addMessage(dto: ChatRoomAddMessageDTO): ChatRoom?
    fun filter(spec: ChatRoomSpec): ChatRoomPaginatedResponse
    fun delete(id: String): String
    fun deleteAll(): String
    fun count(): Long
    fun getByRoomIdAndUserId(roomId: String, userId: String): ChatRoom?
    fun getByUserId(userId: String): PanacheQuery<ChatRoom>
    fun getByName(name: String): PanacheQuery<ChatRoom>
    fun getByType(type: ChatRoomType): PanacheQuery<ChatRoom>
    fun addGroupMember(dto: ChatRoomAddMemberDTO): ChatRoom
    fun removeGroupMember(dto: ChatRoomAddMemberDTO): ChatRoom
    fun changeGroupName(dto: ChatRoomChangeNameDTO): ChatRoom

}