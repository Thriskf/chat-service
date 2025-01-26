package org.elteq.base.utils

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.elteq.base.exception.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.StringWriter
import kotlin.reflect.full.primaryConstructor

class ExportUtil {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)


    /**
     * this re-orders the headers
     */
//    private fun getHeaders(): Array<String> {
//        val excludedFields = listOf("executionResponse", "payload", "headers", "httpMethod", "endpoint", "serviceName", "version")
//        logger.info("field names exclude $excludedFields")
//
//        return try {
//            MakerCheckerDto::class.members.filterIsInstance<kotlin.reflect.KProperty1<MakerCheckerDto, *>>().map { it.name }
//                .filter { it !in excludedFields } // Exclude specified fields
//                .toTypedArray()
//        } catch (e: Exception) {
//            logger.error("Failed to extract headers from MakerCheckerDto: ${e.message}", e)
//            throw ServiceException(-1, "Unable to get headers")
//        }
//    }


    private fun getHeaders(excludedFields: List<String>, targetClass: Any): Array<String> {
//        val excludedFields = listOf("executionResponse", "payload", "headers", "httpMethod", "endpoint", "serviceName", "version")
        logger.info("Field names to exclude: $excludedFields")

        return try {
            targetClass::class.primaryConstructor?.parameters?.mapNotNull { it.name } // Ensure no null values
                ?.filter { it !in excludedFields } // Exclude specified fields
                ?.toTypedArray() ?: emptyArray() // Return empty array if no parameters are found
        } catch (e: Exception) {
            logger.error("Failed to extract headers from MakerCheckerDto: ${e.message}", e)
            throw ServiceException(-1, "Unable to get headers")
        }
    }


    fun generateCsv(listData: List<Any>, targetClass: Any, excludedFields: List<String>): String {
        // Create a StringWriter to store CSV content
        val writer = StringWriter()
        val headers = getHeaders(excludedFields, targetClass)

        return try {
            // Define CSV format
            val format = CSVFormat.DEFAULT.builder().setHeader(*headers).build()

            // Write data using CSVPrinter
            CSVPrinter(writer, format).use { printer ->
                for (data in listData) {
                    val values = headers.map { header ->
                        targetClass::class.members.first {
                            it.name == header
                        }.call(data)
                    }
                    printer.printRecord(values)
                }
            }

            writer.toString()
        } catch (e: Exception) {
            logger.error("could not generate csv file", e)
            throw RuntimeException("Failed to generate CSV", e)
        } finally {
            writer.close()
        }
    }

}