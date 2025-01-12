package org.elteq.base.httpclient

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import lombok.Builder.Default
import org.elteq.base.exception.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.TimeoutException


@ApplicationScoped
class HttpClientV2Impl : HttpClientV2 {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var httpClient: HttpClient? = null

    @Inject
    @field:Default
    private lateinit var putClient: PutClient

    @Inject
    @field:Default
    private lateinit var postClient: PostClient

    override fun send(request: RequestConfig, method: HttpMethod, contentType: ContentType): HttpResponse<String> {
        return when (method) {
            HttpMethod.POST -> post(request, contentType)
            HttpMethod.GET -> get(request)
            HttpMethod.PUT -> put(request, contentType)

            else -> throw IllegalArgumentException("Unsupported method type: ${method.text}")
        }

    }

    override fun get(config: RequestConfig): HttpResponse<String> {
        logger.info("About to build GET request with $config")
        try {
            val request = HttpRequest.newBuilder(URI(config.endpoint))
                .GET() // Use GET method
                .build()
            val response = executeRequest(request)
            return response
        } catch (ex: Exception) {
            logger.error("Error occurred while building GET request : ${ex.message}")
            throw ServiceException(-1, ex.localizedMessage)
        }
    }

    override fun post(config: RequestConfig, contentType: ContentType): HttpResponse<String> {
        return postClient.post(config, contentType)
    }

    override fun put(config: RequestConfig, contentType: ContentType): HttpResponse<String> {
        return putClient.put(config, contentType)
    }

    private fun executeRequest(request: HttpRequest): HttpResponse<String> {
        logger.info("About to send request with body $request")

        try {
            httpClient = HttpClient.newBuilder().build()
            return httpClient?.send(request, HttpResponse.BodyHandlers.ofString())!!
//            val response = httpClient?.send(request, HttpResponse.BodyHandlers.ofString())
//            if (response?.statusCode() in 200..299) {
//                return response!!
//            } else {
//                throw IOException("Unexpected code $response")
//            }
        } catch (tex: TimeoutException) {
            logger.info("Timeout occurred while sending request")
            throw tex
        } catch (ex: Exception) {
            logger.error("Error occurred while sending request : ${ex.message}")
            logger.error("$ex")
            throw ex

        }
    }


}