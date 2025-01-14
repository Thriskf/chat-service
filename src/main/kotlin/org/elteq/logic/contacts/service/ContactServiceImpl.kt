package org.elteq.logic.contacts.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.base.exception.ServiceException
import org.elteq.logic.contacts.db.Contact
import org.elteq.logic.contacts.db.ContactRepository
import org.elteq.logic.contacts.enums.ContactType
import org.elteq.logic.contacts.spec.ContactSpec

@ApplicationScoped
@Transactional
class ContactServiceImpl(@Inject private var repo: ContactRepository) : ContactService {

    override fun findById(id: String): Contact {
        return repo.findById(id) ?: throw ServiceException(-4, "Contact with id $id was not found")
    }


    override fun findByUserId(id: String): PanacheQuery<Contact> {
        return repo.findByUserId(id)
    }


    override fun findByType(type: ContactType): PanacheQuery<Contact> {
        return repo.findByType(type)

    }

    override fun findByValue(value: String): Contact? {
        return repo.findByValue(value)
    }

    override fun search(spec: ContactSpec): PanacheQuery<Contact> {
        return repo.all(spec)
    }

    override fun update(value: String) {
        val contact = findByValue(value)
        contact?.let {
            it.value = value
        }
        repo.entityManager.merge(contact)

    }

    override fun update(contact: PanacheQuery<Contact>) {
//        val clist = contact.stream<Contact>().collect(Collectors.toList())
//        if (!clist.isNullOrEmpty()) {
//            clist.parallelStream().forEach {
////                if (it.type == ContactType.MOBILE) {
////                    it.value = dto.phoneNumber!!
////                }
////                if (it.type == ContactType.EMAIL) {
////                    it.value = dto.email!!
////                }
//            }
//        }
//
//        repo.entityManager.merge(clist)
        TODO()
    }

    override fun update(contact: Contact, value: String) {
        val contact = findById(contact.id!!)
        contact.value = value
        repo.entityManager.merge(contact)
    }

    override fun update(contact: Contact) {
        val contact = findById(contact.id!!)
        repo.entityManager.merge(contact)
    }
}