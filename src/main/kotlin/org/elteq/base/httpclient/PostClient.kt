package org.elteq.base.httpclient

import java.net.http.HttpResponse

interface PostClient {

    fun post(request: RequestConfig, contentType: ContentType): HttpResponse<String>

    fun postJson(config: RequestConfig): HttpResponse<String>

    fun postXml(config: RequestConfig): HttpResponse<String>

    fun postString(config: RequestConfig): HttpResponse<String>

    fun postUrlEncoded(config: RequestConfig): HttpResponse<String>

    fun postFormData(config: RequestConfig): HttpResponse<String>
}