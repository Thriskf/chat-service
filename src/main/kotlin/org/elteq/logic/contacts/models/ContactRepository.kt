package org.elteq.logic.contacts.models

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import org.elteq.logic.contacts.enums.ContactType
import org.elteq.logic.contacts.spec.ContactSpec


@ApplicationScoped
class ContactRepository : PanacheRepositoryBase<Contact, String> {

    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByUserId(id: String): PanacheQuery<Contact> {
        return find("user.id", id)
    }

    fun findByType(type: ContactType): PanacheQuery<Contact> {
        return find("type", type)
    }
    fun findByValue(value: String): Contact? {
        return find("value", value).firstResult()
    }

    fun all(spec: ContactSpec, operation: String = "and"): PanacheQuery<Contact> {
        return paginatedQuery.toQuery(spec, operation, this)
    }
}