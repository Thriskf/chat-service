package org.elteq.logic.chatRoom.spec

/**/import jakarta.ws.rs.QueryParam
import lombok.Data
import org.elteq.base.utils.PaginationDto
import org.elteq.logic.chatRoom.enums.ChatRoomType
import java.time.LocalDateTime

@Data
class ChatRoomSpec : PaginationDto() {
//    @QueryParam("email")
//    var email: String? = null
//
//    @QueryParam("phoneNumber")
//    var phoneNumber: String? = null
//
//    @QueryParam("status")
//    var status: DutyStatus? = null

    @QueryParam("type")
    var type: ChatRoomType? = null

    @QueryParam("name")
    var name: String? = null

//    @QueryParam("from")
//    var from: LocalDateTime? = null
//
//    @QueryParam("to")
//    var to: LocalDateTime? = null

    override fun toMap(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()

        name?.let { params["name"] = name!! }
        type?.let { params["type"] = type!! }
//        status?.let { params["phoneNumber"] = status!! }
//        unit?.let { params["unit"] = unit!! }
//        specialty?.let { params["specialty"] = specialty!! }
//        requestType?.let { params["requestType"] = requestType!! }
//        approvalStatus?.let { params["approvalStatus"] = approvalStatus!! }
//        executionStatus?.let { params["executionStatus"] = executionStatus!! }
        from?.let { params["from"] = from!! }
        to?.let { params["to"] = to!! }
        deleted?.let { params["deleted"] = deleted!! }

        return params
    }

    override fun toString(): String {
        return "ChatRoomSpec(type=$type, name=$name, from=$from, to=$to)"
    }


}