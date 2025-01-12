package org.elteq.logic.activityLog.service


import io.quarkus.hibernate.orm.panache.PanacheQuery
import org.elteq.logic.activityLog.db.ActivityLog
import org.elteq.logic.activityLog.spec.ActivityLogSpec
import java.util.*

interface ActivityLogService {
    fun getById(id: UUID): ActivityLog
    fun all(spec: ActivityLogSpec): PanacheQuery<ActivityLog>
    fun count(): Long

}