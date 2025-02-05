package org.elteq.base.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.vertx.core.json.JsonObject
import jakarta.enterprise.context.ApplicationScoped
import org.json.JSONObject
import org.json.XML
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

@ApplicationScoped
class XmlParser {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val xmlMapper: XmlMapper = XmlMapper()
    private val jsonMapper = ObjectMapper()

    fun parseXmlString(xmlString: String): Document? {
        logger.info("xml string received in parser $xmlString")
        val factory = DocumentBuilderFactory.newInstance()
        factory.isNamespaceAware = true

        return try {
            val builder = factory.newDocumentBuilder()
            logger.info("about to convert xml string to bytearray")
            val byteStream = ByteArrayInputStream(xmlString.toByteArray())
            logger.info("Converting XML string to Document")
            builder.parse(byteStream)
        } catch (e: Exception) {
            logger.error("Error while parsing XML string: ${e.message}", e)
            null // Return null if parsing fails
        }
    }


    fun extractCdataContent(document: Document, cdataTagName: String): String {
        val nodeList: NodeList = document.getElementsByTagName(cdataTagName)
        if (nodeList.length > 0) {
            val element = nodeList.item(0) as Element
            return element.textContent.trim()
        }
        return ""
    }

    fun getElementValue(document: Document, tagName: String): String {
        val nodeList: NodeList = document.getElementsByTagName(tagName)
        if (nodeList.length > 0) {
            val element = nodeList.item(0) as Element
            return element.textContent.trim()
        }
        return ""
    }

    fun xmlToJsonWithJsonXMl(xmlString: String): JSONObject {
        logger.info("xml string received in parser $xmlString")
        return try {
            val json = XML.toJSONObject(xmlString)
//            json.toString(2) // Pretty Print JSON
            json
        } catch (e: Exception) {
            logger.error("Error while parsing XML string: ${e.message}", e)
            JSONObject()
        }
    }

    fun xmlToJsonWithJacksonDataFormat(xmlString: String): JsonObject {
        logger.info("xml string received in parser $xmlString")
        return try {
//            val jsonNode: JsonNode = xmlMapper.readTree(xmlString)
            val jsonNode: JsonNode = xmlMapper.readTree(xmlString.toByteArray())

            val jsonString = jsonMapper.writeValueAsString(jsonNode)
            JsonObject(jsonString)
        } catch (e: Exception) {
            logger.error("Error while parsing JSON string: ${e.message}", e)
            JsonObject()
        }
    }

}