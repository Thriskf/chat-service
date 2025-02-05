//package org.elteq.base.cron
//
//import jakarta.enterprise.context.ApplicationScoped
//import java.io.FileOutputStream
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//
//@ApplicationScoped
//class ExcelGenerator {
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//
//    fun <T : Any> writeExcel(
//        file: String,
//        sheetName: String,
//        headers: List<String>,
//        data: List<T>,
//        mapRecord: (T) -> List<Any?>
//    ): ByteArray {
//        val workbook = XSSFWorkbook()
//        val sheet = workbook.createSheet(sheetName)
//
//        // Create header row
//        val headerRow = sheet.createRow(0)
//        headers.forEachIndexed { index, field ->
//            headerRow.createCell(index).setCellValue(field)
//        }
//        headers.indices.forEach { sheet.autoSizeColumn(it) }
//        logger.info("Herders created")
//
//        // Populate data rows
//        data.forEachIndexed { rowIndex, item ->
//            val row = sheet.createRow(rowIndex + 1)
//            val values = mapRecord(item) // Use callback to map data
//
//            headers.forEachIndexed { colIndex, _ ->
//                val cell = row.createCell(colIndex)
//                cell.setCellValue(values.getOrNull(colIndex)?.toString() ?: "N/A")
//            }
//        }
//        logger.info("Data added")
//
//        val byteArray = try {
//            ByteArrayOutputStream().use { bos ->
//                workbook.write(bos)
//                workbook.close()
//                bos.toByteArray()
//            }
//        } catch (e: Exception) {
//            logger.error("Error while writing workbook to byte array", e)
//            throw e
//        }
//
//
//        FileOutputStream(file).use { fos ->
//            fos.write(byteArray)
//        }
//
//        return byteArray
//    }
//}