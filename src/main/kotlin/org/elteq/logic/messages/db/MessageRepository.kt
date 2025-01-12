package org.elteq.logic.messages.db

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import java.util.*


@ApplicationScoped
class MessageRepository : PanacheRepositoryBase<Messages, UUID> {


    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByEmail(email: String): Messages? {
        return find("email", email).firstResult()
    }

    fun findByMsisdn(phoneNumber: String): Messages? {
        return find("phoneNumber", phoneNumber).firstResult()
    }

//    fun all(spec: Any, operation: String = "and"): PanacheQuery<Any> {
//        if (spec.sortBy == "createdOn") spec.sortBy = "createdOn"
//        return paginatedQuery.toQuery(spec, operation, this)
//    }
}