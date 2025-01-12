package org.elteq.logic.chatRoom.stream.rabbitmq

interface ChatRoomStreamRA {
    fun produce(msg: String)
    fun consume(msg: String)
}