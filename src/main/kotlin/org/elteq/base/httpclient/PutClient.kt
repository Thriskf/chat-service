package org.elteq.base.httpclient
import java.net.http.HttpResponse

interface PutClient {

    fun put(request: RequestConfig, contentType: ContentType): HttpResponse<String>

    fun putJson(config: RequestConfig): HttpResponse<String>

    fun putXml(config: RequestConfig): HttpResponse<String>

    fun putString(config: RequestConfig): HttpResponse<String>

    fun putUrlEncoded(config: RequestConfig): HttpResponse<String>

    fun putFormData(config: RequestConfig): HttpResponse<String>
}