package org.elteq.logic.messages.stream.kakfa

interface MessageStreamKA {
    fun produce(msg: String)
    fun consume(msg: String)
}