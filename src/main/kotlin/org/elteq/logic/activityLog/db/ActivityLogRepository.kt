package org.elteq.logic.activityLog.db

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.utils.PaginatedQuery
import org.elteq.logic.activityLog.spec.ActivityLogSpec
import java.util.*


@ApplicationScoped
class ActivityLogRepository : PanacheRepositoryBase<ActivityLog, UUID> {


    private val paginatedQuery: PaginatedQuery = PaginatedQuery()

    fun findByEmail(email: String): ActivityLog? {
        return find("email", email).firstResult()
    }

    fun findByMsisdn(phoneNumber: String): ActivityLog? {
        return find("phoneNumber", phoneNumber).firstResult()
    }

    fun all(spec: ActivityLogSpec, operation: String = "and"): PanacheQuery<ActivityLog> {
        return paginatedQuery.toQuery(spec, operation, this)
    }
}