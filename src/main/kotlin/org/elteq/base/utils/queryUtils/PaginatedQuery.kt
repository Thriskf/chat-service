package org.elteq.base.utils.queryUtils

import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import io.quarkus.panache.common.Page
import io.quarkus.panache.common.Sort
import jakarta.enterprise.context.ApplicationScoped
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@ApplicationScoped
class PaginatedQuery {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Executes a paginated and sorted query based on the given specifications.
     *
     * @param spec         Pagination and filtering specifications.
     * @param operation    Logical operation to combine filters, default is "and".
     * @param repository   Repository for the entity type.
     */
    fun <T : Any, W : Any, U : PaginationDto> toQuery(
        spec: U,
        operation: String = "and",
        repository: PanacheRepositoryBase<T, W>,
    ): PanacheQuery<T> {

        // Map query parameters from the specification to a key-value format.
        val finalQueryParams = spec.toMap()

        // Build the query string by joining parameters with the specified operation.
//        val query: String = finalQueryParams.entries
//            .joinToString(separator = " $operation ") { "${it.key} = :${it.key}" }

        val query = buildQueryString(finalQueryParams, operation)

        // Log query details for debugging.
        logger.info("Query: $query")
        logger.info("Final Query Parameters: $finalQueryParams")
        logger.info("Page size: ${spec.size}")

        // Setup pagination and sorting.
        val page = Page.of(spec.page, spec.size)
        val sort = if (spec.sortOrder == SortOrder.DESC) Sort.descending(spec.sortBy) else Sort.ascending(spec.sortBy)

        // Return a paginated and optionally filtered query.
        return if (query.isEmpty()) {
            repository.findAll(sort).page(page)
        } else {
            repository.find(query, sort, finalQueryParams).page(page)
        }
    }

    private fun buildQueryString(finalQueryParams: Map<String, Any>, operation: String): String {
        val queryParts = mutableListOf<String>()

        finalQueryParams.forEach { (key, value) ->
            when (key) {
                "from" -> {
                    // Handle the 'from' (start date) filter
                    queryParts.add("createdOn >= :$key") // Replace dateColumn with the actual column name
                }

                "to" -> {
                    // Handle the 'to' (end date) filter
                    queryParts.add("createdOn <= :$key") // Replace dateColumn with the actual column name
                }

                else -> {
                    // Default filtering for other parameters
                    queryParts.add("$key = :$key")
                }
            }
        }

        return queryParts.joinToString(" $operation ")
    }
}

//@ApplicationScoped
//class PaginatedQuery {
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    fun <T : Any, W : Any, U : PaginationDto> toQuery(
//        spec: U,
//        operation: String = "and",
//        repository: PanacheRepositoryBase<T, W>,
//
//        ): PanacheQuery<T> {
//
////           mappingCallback: (queryParam: Map.Entry<String, Any?>) -> String =
//
//        fun mappingCallback(queryParam: Map.Entry<String, Any?>): String {
//            return queryParam.key + " = :" + queryParam.key
//        }
//
//        val finalQueryParams = spec.toMap()
//
//        val query: String = finalQueryParams.entries
//            .stream()
////            .map { mappingCallback(it) }
//            .map { it.key +"= :"+it.key }
//            .collect(Collectors.joining(" $operation "))
//
//        logger.info("query ::: $query")
//        logger.info("finalQueryParams ::: $finalQueryParams")
//        logger.info("page size " + spec.size)
//
//        val page = Page.of(spec.page, spec.size)
//        val sort = if (spec.sortOrder == SortOrder.DESCENDING) Sort.descending(spec.sortBy) else Sort.ascending(spec.sortBy)
//
//        if (query.isEmpty()) {
//            return repository.findAll(sort).page(page)
//        }
//
//
//        return repository.find(query, sort, finalQueryParams).page(page)
//    }
//}