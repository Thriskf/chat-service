package org.elteq.logic.users.service

import io.quarkus.hibernate.orm.panache.PanacheEntity_.id
import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.base.exception.ServiceException
import org.elteq.logic.contacts.db.Contact
import org.elteq.logic.contacts.enums.ContactType
import org.elteq.logic.contacts.service.ContactService
import org.elteq.logic.dob.servcice.DoBService
import org.elteq.logic.users.db.UserRepository
import org.elteq.logic.users.db.Users
import org.elteq.logic.users.models.UserAddDTO
import org.elteq.logic.users.models.UserUpdateContactDTO
import org.elteq.logic.users.models.UserUpdateNameDTO
import org.elteq.logic.users.models.UserUpdateStatusDTO
import org.elteq.logic.users.spec.UserSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

@ApplicationScoped
@Transactional
class UserServiceImpl(@Inject var repo: UserRepository) : UserService {
    @Inject
    @field:Default
    private lateinit var contactService: ContactService


    @Inject
    @field:Default
    private lateinit var dobService: DoBService

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun add(dto: UserAddDTO): Users {

        val userExist = phoneOrEmailLookUp(dto.phoneNumber, dto.email)

        if (userExist) {
            throw ServiceException(
                -1,
                "User already exist with phoneNumber :: ${dto.phoneNumber} or email :: ${dto.email}"
            )
        }


        val ent = Users().apply {
            firstName = dto.firstName
            lastName = dto.lastName
            otherName = dto.otherName
            displayName = dto.displayName
            gender = dto.gender
        }

        ent.dateOfBirth = dto.dateOfBirth?.let {
            val dob = dobService.create(it)
            dob.user = ent
            dob
        }


        val contactSet = mutableSetOf<Contact>()

        val phone = Contact()
        phone.type = ContactType.MOBILE
        phone.value = dto.phoneNumber!!
        phone.user = ent
        contactSet.add(phone)

        val email = Contact()
        email.type = ContactType.EMAIL
        email.value = dto.email!!
        email.user = ent
        contactSet.add(email)

        logger.info("user contact $contactSet")
        ent.contacts = contactSet

        repo.persist(ent)
        logger.info("User added: $ent")
        return ent

    }

    override fun getById(id: UUID): Users {
        return repo.findById(id) ?: throw ServiceException(-2, "User with id $id was not found")
    }

    override fun updateName(dto: UserUpdateNameDTO): Users {

        val ent = getById(dto.id!!)

        if (!dto.firstName.isNullOrBlank()) {
            ent.firstName = dto.firstName
        }
        if (!dto.lastName.isNullOrBlank()) {
            ent.lastName = dto.lastName
        }
        if (!dto.otherName.isNullOrBlank()) {
            ent.otherName = dto.otherName
        }
        if (!dto.displayName.isNullOrBlank()) {
            ent.displayName = dto.displayName
        }

        repo.entityManager.merge(ent)
        logger.info("User updated with names: $ent")
        return ent
    }

    override fun updateContact(dto: UserUpdateContactDTO): Users {
        val ent = getById(dto.id!!)

        if (!dto.email.isNullOrBlank()) {
            val e1 = repo.findByContact(dto.email!!)
            if (e1 != null && e1.id != dto.id) {
                throw ServiceException("Email ${dto.email} already exists")
            }
        }

        if (!dto.phoneNumber.isNullOrBlank()) {
            val e2 = repo.findByContact(dto.phoneNumber!!)
            if (e2 != null && e2.id != dto.id) {
                throw ServiceException("Phone Number ${dto.phoneNumber} already exists")
            }
        }

        val contact = contactService.findByUserId(dto.id!!).stream<Contact>().toList()

        if (contact.isNullOrEmpty()) {
            val conSet = mutableSetOf<Contact>()
            val phone = Contact()
            phone.type = ContactType.MOBILE
            phone.value = dto.phoneNumber!!
            phone.user = ent
            conSet.add(phone)

            val email = Contact()
            email.type = ContactType.EMAIL
            email.value = dto.email!!
            email.user = ent
            conSet.add(email)

            ent.contacts = conSet


        } else {
            contact.parallelStream().forEach {
                if (it.type == ContactType.MOBILE && dto.phoneNumber != null) {
                    contactService.update(it, dto.phoneNumber!!)
                }
                if (it.type == ContactType.EMAIL && dto.email != null) {
                    contactService.update(it, dto.email!!)
                }
            }
        }
        repo.entityManager.merge(ent)
        logger.info("User updated with contact: $ent")
        return ent
    }

    override fun all(spec: UserSpec): PanacheQuery<Users> {
        return repo.all(spec)
    }

    override fun getByContact(contact: String): Users {
        return repo.findByContact(contact) ?: throw ServiceException(-2, "User with id $id was not found")
    }

    override fun delete(id: UUID): String {
        val ent = getById(id)
        repo.delete(ent)
        return "User $id deleted"
    }

    override fun deleteAll(): String {
        repo.deleteAll()
        return "Total number of users deleted ${repo.deleteAll()}"
    }

    override fun count(): Long {
        return repo.count()
    }

    override fun updateStatus(dto: UserUpdateStatusDTO): Users {
        val ent = getById(dto.id!!)
        ent.status = dto.status
        repo.entityManager.merge(ent)
        logger.info("User updated with status: $ent")
        return ent
    }

    private fun phoneOrEmailLookUp(msisdn: String?, email: String?): Boolean {
        email?.takeIf { it.isNotBlank() }?.let {
            if (repo.findByContact(it) != null) return true
        }

        msisdn?.takeIf { it.isNotBlank() }?.let {
            return repo.findByContact(it) != null
        }
        return false
    }
}