package org.elteq.base.utils

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.PositiveOrZero
import jakarta.ws.rs.DefaultValue
import jakarta.ws.rs.QueryParam
import org.hibernate.query.sqm.SortOrder
import java.time.LocalDateTime

abstract class PaginationDto {
    @QueryParam("page")
    @DefaultValue("0")
    @PositiveOrZero
    @Min(value = 0)
    open var page: Int = 0

    @QueryParam("size")
    @DefaultValue("50")
    @PositiveOrZero
    @Min(value = 10)
    @Max(value = 50000)
    open var size: Int = 25

    @QueryParam("sortBy")
    @DefaultValue("createdOn")
    open var sortBy: String = "createdOn"

    @QueryParam("sortOrder")
    @DefaultValue("DESCENDING")
    open var sortOrder: SortOrder = SortOrder.DESCENDING

    @QueryParam("from")
    var from: LocalDateTime? = null

    @QueryParam("deleted")
    var deleted: Boolean? = false

    @QueryParam("to")
    var to: LocalDateTime? = null

    abstract fun toMap(): Map<String, Any>
}