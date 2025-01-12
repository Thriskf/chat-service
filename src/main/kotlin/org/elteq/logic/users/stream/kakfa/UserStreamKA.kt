package org.elteq.logic.users.stream.kakfa

interface UserStreamKA {
    fun produce(msg: String)
    fun consume(msg: String)
}