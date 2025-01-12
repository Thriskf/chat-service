package org.elteq.logic.messages.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import java.util.*

interface MessageService {

    fun add(dto: Any): Any
    fun getById(id: UUID): Any
    fun update(dto: Any): Any
    fun all(spec: Any): PanacheQuery<Any>
    fun getByEmail(email: String): Any
    fun getByPhoneNumber(phoneNumber: String): Any
    fun delete(id: UUID): String
    fun deleteAll(): String
    fun count(): Long
    fun updateStatus(dto: Any): Any

}