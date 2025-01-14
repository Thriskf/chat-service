package org.elteq.logic.users.db

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import org.elteq.logic.users.spec.UserSpec


@ApplicationScoped
class UserRepository : PanacheRepositoryBase<Users, String> {

    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByContact(contact: String): Users? {
//        return find("contacts.value", contact).firstResult()
        return find("SELECT u FROM Users u JOIN u.contacts c WHERE c.value = ?1", contact).firstResult()
//        return find("contacts.value = ?1", contact).firstResult()
    }

    fun findByRoomId(id: String): PanacheQuery<Users> {
        return find("chatRoom.id", id)
    }

    fun all(spec: UserSpec, operation: String = "and"): PanacheQuery<Users> {
//        if (spec.sortBy == "createdOn") spec.sortBy = "createdOn"
        return paginatedQuery.toQuery(spec, operation, this)
    }
}