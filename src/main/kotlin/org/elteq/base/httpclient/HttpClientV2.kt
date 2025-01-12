package org.elteq.base.httpclient

import java.net.http.HttpResponse

interface HttpClientV2 {

    fun send(request: RequestConfig, method: HttpMethod, contentType: ContentType): HttpResponse<String>

    fun get(config: RequestConfig): HttpResponse<String>

    fun post(config: RequestConfig,contentType: ContentType): HttpResponse<String>

    fun put(config: RequestConfig,contentType: ContentType): HttpResponse<String>


}