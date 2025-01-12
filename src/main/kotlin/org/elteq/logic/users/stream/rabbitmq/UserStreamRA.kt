package org.elteq.logic.users.stream.rabbitmq

interface UserStreamRA {
    fun produce(msg: String)
    fun consume(msg: String)
}