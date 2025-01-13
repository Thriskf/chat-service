package org.elteq.logic.messages.db

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import org.elteq.logic.messages.spec.MessageSpec
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

    fun finByUserId(userId: UUID): PanacheQuery<Messages> {
        return find(
            """
        SELECT m FROM Messages m
        JOIN m.user u
        AND u.id = :userId
        """, userId
        )

    }

    fun all(spec: MessageSpec, operation: String = "and"): PanacheQuery<Messages> {
        return paginatedQuery.toQuery(spec, operation, this)
    }
}