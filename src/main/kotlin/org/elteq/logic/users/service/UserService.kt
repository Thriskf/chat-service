package org.elteq.logic.users.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.users.db.Users
import org.elteq.logic.users.models.*
import org.elteq.logic.users.spec.UserSpec
import java.util.*

interface UserService {
    fun add(dto: UserAddDTO): Users
    fun getById(id: UUID): Users
    fun updateName(dto: UserUpdateNameDTO): Users
    fun updateContact(dto: UserUpdateContactDTO): Users
    fun all(spec: UserSpec): PanacheQuery<Users>
    fun getByContact(contact:String): Users
    fun delete(id: UUID): String
    fun deleteAll(): String
    fun count(): Long
    fun updateStatus(dto: UserUpdateStatusDTO): Users

}