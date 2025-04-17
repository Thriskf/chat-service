package org.elteq.logic.dob.db

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.queryUtils.PaginatedQuery


@ApplicationScoped
class DOBRepository : PanacheRepositoryBase<DOB, String> {
    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByUserId(id: String): DOB? {
        return find("patient.id", id).firstResult()
//        return find("patientId", id).firstResult()
    }

//    fun filter(spec: PatientSpec, operation: String = "and"): PanacheQuery<DOB> {
//        if (spec.sortBy == "createdOn") spec.sortBy = "createdOn"
//        return paginatedQuery.toQuery(spec, operation, this)
//    }
}