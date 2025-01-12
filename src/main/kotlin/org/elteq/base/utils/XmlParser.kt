package org.elteq.base.utils

import jakarta.enterprise.context.ApplicationScoped
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory

@ApplicationScoped
class XmlParser {
    fun parseXmlString(xml: String): Document {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        return builder.parse(ByteArrayInputStream(xml.toByteArray()))
    }

    fun getElementValue(document: Document, tagName: String): String {
        val nodeList: NodeList = document.getElementsByTagName(tagName)

        if (nodeList.length > 0) {
            val element = nodeList.item(0) as Element
            return element.textContent.trim()
        }

        return ""
    }
}