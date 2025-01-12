package org.elteq.logic.messages.service

import io.quarkus.hibernate.orm.panache.PanacheQuery
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.elteq.logic.messages.db.MessageRepository
import java.util.*

@ApplicationScoped
@Transactional
class MessageServiceImpl(@Inject var repository: MessageRepository) : MessageService {
    override fun add(dto: Any): Any {
        TODO("Not yet implemented")
    }

    override fun getById(id: UUID): Any {
        TODO("Not yet implemented")
    }

    override fun update(dto: Any): Any {
        TODO("Not yet implemented")
    }

    override fun all(spec: Any): PanacheQuery<Any> {
        TODO("Not yet implemented")
    }

    override fun getByEmail(email: String): Any {
        TODO("Not yet implemented")
    }

    override fun getByPhoneNumber(phoneNumber: String): Any {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID): String {
        TODO("Not yet implemented")
    }

    override fun deleteAll(): String {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun updateStatus(dto: Any): Any {
        TODO("Not yet implemented")
    }
}