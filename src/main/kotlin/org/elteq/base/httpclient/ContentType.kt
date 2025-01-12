package org.elteq.base.httpclient

enum class ContentType(val text: String) {
    JSON("application/json"),
    XML("application/xml"),
    XML_TEXT("text/xml"),
    URL_ENCODED("application/x-www-form-urlencoded"),
    FORM_DATA("multipart/form-data"),
    TEXT("text/plain")
}