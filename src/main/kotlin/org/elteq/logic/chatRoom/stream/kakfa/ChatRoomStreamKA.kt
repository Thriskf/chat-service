package org.elteq.logic.chatRoom.stream.kakfa

interface ChatRoomStreamKA {
    fun produce(msg: String)
    fun consume(msg: String)
}