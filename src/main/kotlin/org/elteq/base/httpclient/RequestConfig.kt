package org.elteq.base.httpclient

import java.io.Serializable

class RequestConfig(
    val endpoint: String,
    val params: Any? = null,
    val headers: HashMap<String, String>? = null,
) : Serializable {
    override fun toString(): String {
        return "RequestConfig(endpoint='$endpoint', params=$params, headers=$headers)"
    }
}