package org.elteq.logic.contacts.spec

import jakarta.ws.rs.QueryParam
import lombok.Data
import org.elteq.base.utils.PaginationDto
import org.elteq.logic.contacts.enums.ContactType
import java.time.LocalDateTime

@Data
class ContactSpec : PaginationDto() {
    @QueryParam("value")
    var value: String? = null

    @QueryParam("contactType")
    var contactType: ContactType? = null

    override fun toMap(): Map<String, Any> {
        val params = mutableMapOf<String, Any>()

        value?.let { params["value"] = value!! }
        contactType?.let { params["contactType"] = contactType!! }
        from?.let { params["from"] = from!! }
        to?.let { params["to"] = to!! }

        return params
    }

    override fun toString(): String {
        return "ContactSpec(value=$value, contactType=$contactType, from=$from, to=$to)"
    }


}