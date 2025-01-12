package org.elteq.logic.dob.servcice

import org.elteq.logic.dob.db.DOB
import org.elteq.logic.dob.models.DoBDTO
import java.util.*

interface DoBService {
    fun create(dto: DoBDTO): DOB
    fun update(dto: DoBDTO, patientId: UUID): DOB
    fun getById(id: UUID): DOB?
    fun getByPatientId(id: UUID): DOB?
    fun findAll(): List<DOB>
    fun deleteAll(): Long
    fun deleteById(id: UUID): String
    fun deleteByPatientId(id: UUID): String
}