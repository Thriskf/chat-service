package org.elteq.logic.users.spec

import jakarta.ws.rs.QueryParam
import lombok.Data
import org.elteq.base.utils.PaginationDto
import org.elteq.logic.users.enums.Status
import java.time.LocalDateTime

@Data
class UserSpec : PaginationDto() {
//    @QueryParam("email")
//    var email: String? = null
//
//    @QueryParam("phoneNumber")
//    var phoneNumber: String? = null
//
    @QueryParam("status")
    var status: Status? = null
//
//    @QueryParam("unit")
//    var unit: Unit? = null
//
//    @QueryParam("specialty")
//    var specialty: Specialty? = null
//
    @QueryParam("from")
    var from: LocalDateTime? = null

    @QueryParam("to")
    var to: LocalDateTime? = null

    override fun toMap(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()

        status?.let { params["status"] = status!! }
//        phoneNumber?.let { params["phoneNumber"] = phoneNumber!! }
//        status?.let { params["phoneNumber"] = status!! }
//        unit?.let { params["unit"] = unit!! }
//        specialty?.let { params["specialty"] = specialty!! }
//        requestType?.let { params["requestType"] = requestType!! }
//        approvalStatus?.let { params["approvalStatus"] = approvalStatus!! }
//        executionStatus?.let { params["executionStatus"] = executionStatus!! }
        from?.let { params["from"] = from!! }
        to?.let { params["to"] = to!! }

        return params
    }

    override fun toString(): String {
        return "UserSpec(status=$status, from=$from, to=$to)"
    }


}