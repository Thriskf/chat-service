package org.elteq.logic.dob.servcice

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.elteq.base.exception.ServiceException
import org.elteq.logic.dob.db.DOB
import org.elteq.logic.dob.db.DOBRepository
import org.elteq.logic.dob.models.DoBDTO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

@ApplicationScoped
@Transactional
class DoBServiceImpl(private val repo: DOBRepository) : DoBService {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    override fun create(dto: DoBDTO): DOB {
        logger.info("Creating DOB  with payload: $dto")

        try {
//            val ent = DOB()
//            ent.day = dto.day
//            ent.month = dto.month
//            ent.year = dto.year

            val ent = DOB().apply {
                day = dto.day
                month = dto.month
                year = dto.year
            }

            repo.persistAndFlush(ent)
            return ent
        }catch (e: Exception) {
            logger.error("Error creating DOB: $e")
            throw ServiceException("DOB could not be created")
        }

    }

    override fun update(dto: DoBDTO, userId: String): DOB {
        logger.info("Updating DOB for patientId: $userId with payload: $dto")
        val ent = this.getByPatientId(userId)
        logger.info("DOB for patientId: $userId with payload: $ent")

        if (ent == null) {
            logger.info("no dob found for patientId: $userId -- creating one")
            return create(dto)
        }

        ent.day = dto.day
        ent.month = dto.month
        ent.year = dto.year

        repo.entityManager.merge(ent)
        return ent
    }

    override fun getById(id: String): DOB? {
        logger.info("Getting DOB for wardId: $id")
        return repo.findById(id)
    }

    override fun getByPatientId(id: String): DOB? {
        logger.info("Getting DOB for patient with wardId : $id")
        return repo.findByUserId(id)
    }

    override fun findAll(): List<DOB> {
        logger.info("Getting all DOBs")

        val entities = repo.findAll().stream<DOB>()
            .collect(Collectors.toList())
        logger.info("Found ${entities.size} DOBs")
        logger.info("DOBS found $entities")
        return entities
    }

    override fun deleteAll(): Long {
        logger.info("Deleting all DOBs")
        return repo.deleteAll()
    }

    override fun deleteById(id: String): String {
        logger.info("Deleting DOB for wardId: $id")
        val ent = getById(id)
        logger.info("DOB to be deleted $ent")
        repo.delete(ent)
        return "DOB with the wardId $id was deleted"
    }

    override fun deleteByUserId(id: String): String {
        logger.info("Deleting DOB for patient with wardId: $id")
        val ent = getByPatientId(id)
        logger.info("DOB to be deleted $ent")
        repo.delete(ent)
        return "DOB with the patient wardId $id was deleted"
    }
}