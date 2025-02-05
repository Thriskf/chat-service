package org.elteq.base.utils

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.elteq.base.exception.ServiceException
import org.elteq.logic.contacts.db.Contact
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.StringWriter
import kotlin.reflect.full.primaryConstructor

class ExportUtil {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    private fun getHeaders(targetClass: Any, excludedFields: Set<String>): Array<String> {
        logger.info("Getting headers")
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


    fun generateCsv(listData: List<Any>, targetClass: Any, excludedFields: Set<String>): String {
        logger.info("Generating CSV data")

        val writer = StringWriter()
        val headers = getHeaders(targetClass, excludedFields)

        return try {
            val format = CSVFormat.DEFAULT.builder().setHeader(*headers).build()

            CSVPrinter(writer, format).use { printer ->
                for (data in listData) {
                    val values = headers.map { header ->
                        getPropertyValue(data, header, targetClass)
                    }
                    logger.info("data written  to file $values")
                    printer.printRecord(values)
                }
            }
            logger.info("done generating CSV data")
            writer.toString()
        } catch (e: Exception) {
            logger.error("could not generate csv file", e)
            throw ServiceException(-3, "Failed to generate CSV", e)
        } finally {
            logger.info("closing csv writer")
            writer.close()
        }
    }


    private fun getPropertyValue(data: Any, header: String, targetClass: Any): String {
        logger.info("Getting values for $header")
        val property = targetClass::class.members.firstOrNull { it.name == header }
        return when (header) {
//            "amount" -> {
//                val amount = property?.call(data) as? TransactionPriceDto
//                amount?.value?.toString() ?: ""
//            }

            "contacts" -> {
                val contacts = property?.call(data) as? Set<Contact>
                // Extract specific values from the contacts (e.g., emails)
                contacts?.joinToString(";") { it.value } ?: ""
            }

            else -> property?.call(data)?.toString() ?: ""
        }
    }


//    fun generateCsv(dataList: List<TransactionDto>): String {
//        // Create a StringWriter to store CSV content
//        val writer = StringWriter()
//        val headers = getHeaders()
//
//        return try {
//            // Define CSV format
//            val format = CSVFormat.DEFAULT.builder().setHeader(*headers).build()
//
//            // Write data using CSVPrinter
//            CSVPrinter(writer, format).use { printer ->
//                for (data in dataList) {
//                    val values = headers.map { header ->
//                        TransactionDto::class.members.first { it.name == header }.call(data)
//                    }
//                    printer.printRecord(values)
//                }
//            }
//
//            writer.toString()
//        } catch (e: Exception) {
//            logger.error("could not generate csv file", e)
//            throw RuntimeException("Failed to generate CSV", e)
//        } finally {
//            writer.close()
//        }
//    }

}