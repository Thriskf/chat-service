package org.elteq.logic.dob.servcice

import org.elteq.logic.dob.db.DOB
import org.elteq.logic.dob.models.DoBDTO

interface DoBService {
    fun create(dto: DoBDTO): DOB
    fun update(dto: DoBDTO, userId: String): DOB
    fun getById(id: String): DOB?
    fun getByPatientId(id: String): DOB?
    fun findAll(): List<DOB>
    fun deleteAll(): Long
    fun deleteById(id: String): String
    fun deleteByUserId(id: String): String
}