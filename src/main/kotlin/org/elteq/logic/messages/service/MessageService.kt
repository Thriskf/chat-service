package org.elteq.logic.messages.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.messages.db.Messages
import org.elteq.logic.messages.models.MessageAddDTO
import org.elteq.logic.messages.models.MessageUpdateDTO
import org.elteq.logic.messages.spec.MessageSpec
import java.util.*

interface MessageService {

    fun add(dto: MessageAddDTO): Messages
    fun add(msg:String, roomId:UUID,userId:UUID): Messages
    fun getById(id: UUID): Messages?
    fun update(dto: MessageUpdateDTO): Messages
    fun all(spec: MessageSpec): PanacheQuery<Messages>
    fun delete(id: UUID): String
    fun deleteAll(): String
    fun count(): Long
    fun updateStatus(dto: Any): Messages

}