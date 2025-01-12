package org.elteq.logic.contacts.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.contacts.db.Contact
import org.elteq.logic.contacts.enums.ContactType
import org.elteq.logic.contacts.spec.ContactSpec
import java.util.*

interface ContactService {
    fun findById(id: UUID):Contact
    fun findByUserId(id: UUID): PanacheQuery<Contact>
    fun findByType(type: ContactType): PanacheQuery<Contact>
    fun findByValue(value:String): Contact?
    fun search(spec:ContactSpec): PanacheQuery<Contact>
    fun update(value: String)
    fun update(contact: PanacheQuery<Contact>)
    fun update(contact: Contact,value: String)
    fun update(contact: Contact)
}