package org.elteq.logic.users.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.users.models.Users
import org.elteq.logic.users.dtos.UserAddDTO
import org.elteq.logic.users.dtos.UserUpdateContactDTO
import org.elteq.logic.users.dtos.UserUpdateNameDTO
import org.elteq.logic.users.dtos.UserUpdateStatusDTO
import org.elteq.logic.users.spec.UserSpec

interface UserService {
    fun add(dto: UserAddDTO): Users
    fun getById(id: String): Users
    fun updateName(dto: UserUpdateNameDTO): Users
    fun updateContact(dto: UserUpdateContactDTO): Users
    fun all(spec: UserSpec): PanacheQuery<Users>
    fun getByContact(contact:String): Users
    fun delete(id: String): String
    fun deleteAll(): String
    fun count(): Long
    fun updateStatus(dto: UserUpdateStatusDTO): Users
    fun export(spec: UserSpec):String

}