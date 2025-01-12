package org.elteq.logic.activityLog.spec

import jakarta.ws.rs.QueryParam
import lombok.Data
import org.elteq.base.utils.PaginationDto


@Data
class ActivityLogSpec : PaginationDto() {
    @QueryParam("email")
    var email: String? = null

    @QueryParam("phoneNumber")
    var phoneNumber: String? = null

    @QueryParam("unit")
    var unit: Unit? = null


//
//    @QueryParam("from")
//    var from: LocalDateTime? = null
//
//    @QueryParam("to")
//    var to: LocalDateTime? = null

    override fun toMap(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()

        email?.let { params["email"] = email!! }
        phoneNumber?.let { params["phoneNumber"] = phoneNumber!! }
//        status?.let { params["phoneNumber"] = status!! }
        unit?.let { params["unit"] = unit!! }
//        requestType?.let { params["requestType"] = requestType!! }
//        approvalStatus?.let { params["approvalStatus"] = approvalStatus!! }
//        executionStatus?.let { params["executionStatus"] = executionStatus!! }
//        from?.let { params["from"] = from!! }
//        to?.let { params["to"] = to!! }

        return params
    }

    override fun toString(): String {
        return "ActivityLogSpec(email=$email, phoneNumber=$phoneNumber, unit=$unit)"
    }


}