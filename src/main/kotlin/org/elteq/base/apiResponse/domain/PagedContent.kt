package org.elteq.base.apiResponse.domain

import io.quarkus.panache.common.Page
import java.io.Serializable

class PagedContent<T> : Serializable {
    var totalElements: Long = 0
    var totalPages = 0
    var page = 0
    var size = 0
    var hasNextPage = false
    var hasPreviousPage = false
    var isFirst = false
    var isLast = false
    var data: List<T>? = null

    constructor(pagedData: Page, data: List<T>?) {
        this.page = pagedData.index
        this.size = pagedData.size
        this.data = data
    }

}