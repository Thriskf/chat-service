package org.elteq.logic.chatRoom.dtos

import org.elteq.base.apiResponse.domain.PaginatedBaseResponse
import org.elteq.base.apiResponse.domain.BaseResponse

class ChatRoomResponse(data: List<ChatRoomDTO>?) : BaseResponse() {
    val data: List<ChatRoomDTO>? = data
}

class ChatRoomPaginatedResponse(data: List<ChatRoomDTO>?) : PaginatedBaseResponse() {
    val data: List<ChatRoomDTO>? = data
}