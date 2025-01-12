package org.elteq.logic.messages.stream.rabbitmq

interface MessageStreamRA {
    fun produce(msg: String)
    fun consume(msg: String)
}