package org.elteq.logic.activityLog.db

import io.quarkus.hibernate.orm.panache.PanacheEntityBase
import jakarta.persistence.*
import org.elteq.logic.activityLog.enums.ActivityType
import org.elteq.logic.activityLog.enums.LogSeverity
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(
    name = "tbl_activity_log",
    indexes = [
        Index(name = "idx_logs_activity_type", columnList = "activity_type"),
        Index(name = "idx_logs_user_id", columnList = "user_id"),
        Index(name = "idx_logs_reference_id", columnList = "reference_id")
    ]
)
class ActivityLog : PanacheEntityBase(), Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null


    @Column(name = "activity_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var activityType: ActivityType? = null // CREATE, UPDATE, DELETE, etc.

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    var description: String? = null // A detailed explanation of the activity

    @Column(name = "reference_id")
    var referenceId: UUID? = null // Reference to the entity affected (e.g., Admission, Patient)

    @Column(name = "reference_table")
    var referenceTable: String? = null // Name of the affected table/entity

    @Column(name = "user_id", nullable = false)
    var userId: UUID? = null // The user who performed the activity

    @Column(name = "user_role")
    var userRole: String? = null // Role of the user (e.g., DOCTOR, NURSE, ADMIN)

    @Column(name = "ip_address")
    var ipAddress: String? = null // IP address of the user (if applicable)

    @Column(name = "device_info")
    var deviceInfo: String? = null // Information about the device used

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    var severity: LogSeverity? = LogSeverity.INFO // Severity of the activity

    @CreationTimestamp
    @Column(name = "created_on")
    var createdOn: LocalDateTime? = null

    @Column(name="deleted")
    var deleted:Boolean = false

    @UpdateTimestamp
    @Column(name = "updated_on")
    var updatedOn: LocalDateTime? = null
    override fun toString(): String {
        return "Log(id=$id, activityType=$activityType, description=$description, referenceId=$referenceId, referenceTable=$referenceTable, " +
                "userId=$userId, userRole=$userRole, ipAddress=$ipAddress, deviceInfo=$deviceInfo, severity=$severity, createdOn=$createdOn, updatedOn=$updatedOn)"
    }

}