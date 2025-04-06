package org.elteq.logic.messages.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.messages.models.Messages
import org.elteq.logic.messages.dtos.MessageAddDTO
import org.elteq.logic.messages.dtos.MessageUpdateDTO
import org.elteq.logic.messages.spec.MessageSpec

interface MessageService {

    fun add(dto: MessageAddDTO): Messages
    fun add(msg:String, roomId:String,userId:String): Messages
    fun getById(id: String): Messages?
    fun update(dto: MessageUpdateDTO): Messages
    fun all(spec: MessageSpec): PanacheQuery<Messages>
    fun getByUser(userId: String): PanacheQuery<Messages>
    fun delete(id: String): String
    fun deleteAll(): String
    fun count(): Long
    fun updateStatus(dto: Any): Messages

}