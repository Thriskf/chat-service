package org.elteq.logic.messages.models

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import org.elteq.logic.messages.spec.MessageSpec


@ApplicationScoped
class MessageRepository : PanacheRepositoryBase<Messages, String> {


    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByUserId(userId: String): PanacheQuery<Messages> {
        return find(
            """
        SELECT m FROM Messages m
        JOIN m.user u
        WHERE u.id = ?1
        """, userId
        )
    }


    fun all(spec: MessageSpec, operation: String = "and"): PanacheQuery<Messages> {
        return paginatedQuery.toQuery(spec, operation, this)
    }
}