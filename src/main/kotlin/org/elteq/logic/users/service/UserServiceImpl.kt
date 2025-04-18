package org.elteq.logic.users.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.elteq.base.apiResponse.domain.ResponseMessage
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.ExportUtil
import org.elteq.base.utils.MapperUtil.Mapper
import org.elteq.base.utils.PasswordUtils
import org.elteq.base.utils.email.EmailDTO
import org.elteq.base.utils.email.EmailService
import org.elteq.base.utils.email.HtmlEmailTemplates
import org.elteq.logic.auth.dtos.UserChangePasswordDTO
import org.elteq.logic.auth.dtos.UserForgetPasswordDTO
import org.elteq.logic.contacts.enums.ContactType
import org.elteq.logic.contacts.models.Contact
import org.elteq.logic.contacts.service.ContactService
import org.elteq.logic.dob.servcice.DoBService
import org.elteq.logic.users.dtos.*
import org.elteq.logic.users.enums.Status
import org.elteq.logic.users.models.Credentials
import org.elteq.logic.users.models.UserRepository
import org.elteq.logic.users.models.Users
import org.elteq.logic.users.spec.UserSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.stream.Collectors

@ApplicationScoped
//@Singleton
class UserServiceImpl(
    @Inject var repo: UserRepository,
    @Inject var emailService: EmailService,
) : UserService {
    @Inject
    @field:Default
    private lateinit var contactService: ContactService

    private val modelMapper = Mapper.mapper
    private val passwordUtils = PasswordUtils

    @Inject
    @field:Default
    private lateinit var dobService: DoBService

    @ConfigProperty(name = "temp.password.expire.time", defaultValue = "5")
    private lateinit var tempExpirePass: String

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
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

//        ent.dateOfBirth = dto.dateOfBirth?.let {
//            val dob = dobService.create(it)
//            dob.user = ent
//            dob
//        }


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
        ent.contacts = contactSet

        val tmpPassword = passwordUtils.generateTemporaryPassword(10)
        val passwordCredential = Credentials().apply {
            this.user = ent
            this.password = passwordUtils.hashPassword(tmpPassword)
            this.expiresIn = LocalDateTime.now().plusMinutes(tempExpirePass.toLong())
        }
        passwordCredential.persistAndFlush()

        ent.password = passwordCredential

        logger.info("user contact $contactSet")
        repo.persist(ent)

        runCatching {
            val emailDto = EmailDTO(email.value, ent.firstName, "Signup Successful.", "")
            val mail = HtmlEmailTemplates.signUp(ent.firstName!!, tmpPassword)
            emailService.sendHtmlMail(emailDto, mail)


        }.fold(onSuccess = {
            logger.info("User signup email sent successfully ${email.value}")
        }, onFailure = {
            logger.error("error occurred while sending signup email", it)
        })
        logger.info("User added: $ent")
        return ent

    }

    @Transactional
    override fun getById(id: String): Users {
        return repo.findById(id) ?: throw ServiceException(-2, "User with id $id was not found")
    }

    @Transactional
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

    @Transactional
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
//            contact.parallelStream().forEach {
            contact.forEach {
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
        return repo.findByContact(contact) ?: throw ServiceException(-2, "User with contact $contact was not found")
    }

    override fun delete(id: String): String {

        return runCatching {
            val ent = getById(id)
            ent.deleted = true
            ent.contacts?.forEach {
                it.deleted = true
                it.persistAndFlush()
            }

            repo.entityManager.merge(ent)
        }.fold(onSuccess = {
            "User deleted: ${it.id} was successfully deleted"
        }, onFailure = {
            logger.error("Error deleting user", it)
            "Could not delete user with id $id."
        })

    }

    override fun deleteAll(): String {
        repo.deleteAll()
        return "Total number of users deleted ${repo.deleteAll()}"
    }

    override fun count(): Long {
        return repo.count()
    }

    @Transactional
    override fun updateStatus(dto: UserUpdateStatusDTO): Users {
        val ent = getById(dto.id!!)
        ent.status = dto.status
        repo.entityManager.merge(ent)
        logger.info("User updated with status: $ent")
        return ent
    }

    private fun phoneOrEmailLookUp(msisdn: String?, email: String?): Boolean {
        email?.takeUnless { it.isBlank() }?.let {
            if (repo.findByContact(it) != null) return true
        }

        msisdn?.takeIf { it.isNotBlank() }?.let {
            return repo.findByContact(it) != null
        }
        return false
    }

    override fun export(spec: UserSpec): String {
        logger.info("about to export data")
        val ents = this.all(spec)

        val data = ents.stream<Users>().map {
            logger.info("mapped users: $it to dto:")
            modelMapper.map(it, UserDTO::class.java)
        }.collect(Collectors.toList())
        val excludedFields: Set<String> = setOf("contacts")

        return ExportUtil().generateCsv(data, UserDTO(), excludedFields)

    }

    @Transactional
    override fun resetPassword(dto: UserForgetPasswordDTO): UserResponse {

        val data = mutableListOf<UserDTO>()
        var message = ResponseMessage.SUCCESS.message
        var code = ResponseMessage.SUCCESS.code
        var systemMessage = "Password reset successfully. New temporal password sent to email"
        var systemCode = ResponseMessage.SUCCESS.code

        logger.info("Reset User ${dto.email} password")
        var tmpPassword: String? = null

        val userResetResult = runCatching {
            val user = dto.email?.let { getByContact(it) }

            if (user == null) {
                return UserResponse(data).apply {
                    this.code = ResponseMessage.FAIL.code
                    this.systemCode = ResponseMessage.FAIL.code
                    this.message = ResponseMessage.FAIL.message
                    this.systemMessage = "User Not Found"
                }
            }

            tmpPassword = passwordUtils.generateTemporaryPassword(10)
            user.password?.password = passwordUtils.hashPassword(tmpPassword!!)
            user.password?.expiresIn = LocalDateTime.now().plusMinutes(tempExpirePass.toLong())
            user.fcp = true

            repo.entityManager.merge(user)
        }

        userResetResult.fold(
            onSuccess = {
                logger.info("User password reset successful for ${dto.email}")
            },
            onFailure = {
                logger.error("Password reset failed for ${dto.email}", it)
                message = ResponseMessage.FAIL.message
                code = ResponseMessage.FAIL.code
                systemMessage = "Could not reset password"
                systemCode = ResponseMessage.FAIL.code
            }
        )

        if (userResetResult.isSuccess && tmpPassword != null) {
            runCatching {
                val emailDto = EmailDTO(dto.email, userResetResult.getOrNull()?.firstName, "Password Reset Successful", "")
                val mail = HtmlEmailTemplates.resetPassword(tmpPassword!!)
                emailService.sendHtmlMail(emailDto, mail)
            }.fold(
                onSuccess = {
                    logger
                        .info("Password reset email sent to ${dto.email}")
                },
                onFailure = {
                    logger.error("Failed to send password reset email to ${dto.email}", it)
                }
            )
        }

        val response = UserResponse(data)
        response.code = code
        response.systemCode = systemCode
        response.message = message
        response.systemMessage = systemMessage
        return response

    }

    @Transactional
    override fun updatePassword(dto: UserChangePasswordDTO): UserResponse {
        logger.info("Updating user password for ${dto.userId}")

        val data = mutableListOf<UserDTO>()

        val user = dto.userId?.let { getById(it) }

        val verified = verifyPassword(dto.currentPassword!!, user?.password?.password!!)

        if (!verified) {
            return UserResponse(data).apply {
                this.code = ResponseMessage.FAIL.code
                this.systemCode = ResponseMessage.FAIL.code
                this.message = ResponseMessage.FAIL.message
                this.systemMessage = "Password mismatch"
            }
        }

        var email = ""

        return runCatching {
            if (user.firstLogin!!) {
                user.firstLogin = false
                user.status = Status.VERIFIED
            }

            if (user.fcp) {
                val isExpired = LocalDateTime.now() >= user.password!!.expiresIn
                if (isExpired) {
                    return UserResponse(data).apply {
                        this.code = ResponseMessage.FAIL.code
                        this.systemCode = ResponseMessage.FAIL.code
                        this.message = ResponseMessage.FAIL.message
                        this.systemMessage = "Temporary Password Expired. Please reset Password"
                    }
                }
                user.fcp = false
            }

            user.password?.password = dto.newPassword?.let { passwordUtils.hashPassword(it) }
            repo.entityManager.merge(user)
            modelMapper.map(user, UserDTO::class.java)
        }.fold(
            onSuccess = { dto ->
                data.add(dto)

                // Send email
                runCatching {
                    email = user?.contacts
                        ?.firstOrNull { it.type == ContactType.EMAIL }
                        ?.value.toString()

                    val emailDto = EmailDTO(
                        recipientName = user?.firstName,
                        recipientEmail = email,
                        subject = "Password Change Confirmation",
                        body = "Your password was successfully changed. If this wasn't you, please contact support immediately."
                    )

                    val mail = HtmlEmailTemplates.updatePassword(user.firstName!!)
                    emailService.sendHtmlMail(emailDto, mail)
                }.onSuccess {
                    logger.info("Password change confirmation email sent to $email")
                }.onFailure {
                    logger.error("Failed to send password change confirmation email", it)
                }


                UserResponse(data).apply {
                    this.code = ResponseMessage.SUCCESS.code
                    this.systemCode = ResponseMessage.SUCCESS.code
                    this.message = ResponseMessage.SUCCESS.message
                    this.systemMessage = "Password Changed successfully"
                }
            },
            onFailure = {
                logger.error("Unable to change password", it)
                UserResponse(data).apply {
                    this.code = ResponseMessage.FAIL.code
                    this.systemCode = ResponseMessage.FAIL.code
                    this.message = ResponseMessage.FAIL.message
                    this.systemMessage = "Could not change password"
                }
            }
        )

    }

    override fun verifyPassword(password: String, hashedPassword: String): Boolean {
        return runCatching {
            passwordUtils.hashPassword(password)
        }.fold(
            onSuccess = {
                it == hashedPassword
            }, onFailure = {
                logger.warn("Could not verify password", it)
                false
            }
        )
    }
}