package org.elteq.logic.messages.spec

import jakarta.ws.rs.QueryParam
import lombok.Data
import org.elteq.base.utils.PaginationDto
import java.time.LocalDateTime


@Data
class MessageSpec : PaginationDto(){
    @QueryParam("from")
    var from: LocalDateTime? = null

    @QueryParam("to")
    var to: LocalDateTime? = null

    override fun toMap(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()


//        requestType?.let { params["requestType"] = requestType!! }
//        approvalStatus?.let { params["approvalStatus"] = approvalStatus!! }
//        executionStatus?.let { params["executionStatus"] = executionStatus!! }
        from?.let { params["from"] = from!! }
        to?.let { params["to"] = to!! }

        return params
    }

    override fun toString(): String {
        return "MessageSpec(from=$from, to=$to)"
    }


}