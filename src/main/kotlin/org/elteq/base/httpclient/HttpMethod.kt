package org.elteq.base.httpclient

enum class HttpMethod(val text: String) {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
}