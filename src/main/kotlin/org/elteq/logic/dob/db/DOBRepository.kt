package org.elteq.logic.dob.db

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import java.util.*


@ApplicationScoped
class DOBRepository : PanacheRepositoryBase<DOB, UUID> {
    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByPatientId(id: UUID): DOB? {
        return find("patient.id", id).firstResult()
//        return find("patientId", id).firstResult()
    }

//    fun all(spec: PatientSpec, operation: String = "and"): PanacheQuery<DOB> {
//        if (spec.sortBy == "createdOn") spec.sortBy = "createdOn"
//        return paginatedQuery.toQuery(spec, operation, this)
//    }
}