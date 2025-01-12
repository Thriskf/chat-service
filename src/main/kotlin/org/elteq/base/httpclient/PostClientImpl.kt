package org.elteq.base.httpclient

import jakarta.enterprise.context.ApplicationScoped
import org.elteq.base.exception.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeoutException
import java.util.stream.Collectors

@ApplicationScoped
class PostClientImpl : PostClient {

    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private var httpClient: HttpClient? = null

    override fun post(request: RequestConfig, contentType: ContentType): HttpResponse<String> {
        return when (contentType) {
            ContentType.FORM_DATA -> postFormData(request)
            ContentType.URL_ENCODED -> postUrlEncoded(request)
            ContentType.TEXT -> postString(request)
            ContentType.XML -> postXml(request)
            ContentType.JSON -> postJson(request)

            else -> throw IllegalArgumentException("Unsupported content type: $contentType")
        }
    }

    override fun postJson(config: RequestConfig): HttpResponse<String> {
        val requestBody = buildPostRequest(config, ContentType.JSON.text)
        val response = executeRequest(requestBody)
        return response
    }

    override fun postXml(config: RequestConfig): HttpResponse<String> {
        val requestBody = buildPostRequest(config, ContentType.XML_TEXT.text)
        val response = executeRequest(requestBody)
        return response
    }

    override fun postString(config: RequestConfig): HttpResponse<String> {
        val requestBody = buildPostRequest(config, ContentType.TEXT.text)
        val response = executeRequest(requestBody)
        return response
    }

    override fun postUrlEncoded(config: RequestConfig): HttpResponse<String> {
        val requestBody = buildPostRequest(config, ContentType.URL_ENCODED.text)
        val response = executeRequest(requestBody)
        return response
    }

    override fun postFormData(config: RequestConfig): HttpResponse<String> {
        val requestBody = buildPostRequest(config, ContentType.FORM_DATA.text)
        val response = executeRequest(requestBody)
        return response
    }

    private fun buildPostRequest(requestParams: RequestConfig, contentType: String): HttpRequest {
        logger.info("About to build request body with $requestParams")
        try {
            val requestBody: HttpRequest = when (contentType) {
                ContentType.JSON.text -> {
                    HttpRequest.newBuilder(URI(requestParams.endpoint))
                        .header("Content-Type", contentType)
                        .POST(
                            HttpRequest.BodyPublishers.ofString(
                                requestParams.params.toString(),
                                StandardCharsets.UTF_8
                            )
                        )
                        .build()
                }

                ContentType.XML_TEXT.text -> {
                    HttpRequest.newBuilder(URI(requestParams.endpoint))
                        .header("Content-Type", contentType)
                        .POST(
                            HttpRequest.BodyPublishers.ofString(
                                requestParams.params.toString(),
                                StandardCharsets.UTF_8
                            )
                        )
                        .build()
                }

                ContentType.TEXT.text -> {
                    HttpRequest.newBuilder(URI(requestParams.endpoint))
                        .header("Content-Type", contentType)
                        .POST(
                            HttpRequest.BodyPublishers.ofString(
                                requestParams.params.toString(),
                                StandardCharsets.UTF_8
                            )
                        )
                        .build()
                }

                ContentType.URL_ENCODED.text -> {
                    // Assuming requestParams.params is a Map<String, String> for URL-encoded form data
                    val formData = (requestParams.params as Map<String, String>).entries.stream()
                        .map { entry -> "${entry.key}=${entry.value}" }
                        .collect(Collectors.joining("&"))
                    HttpRequest.newBuilder(URI(requestParams.endpoint))
                        .header("Content-Type", contentType)
                        .POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8))
                        .build()
                }

                ContentType.FORM_DATA.text -> {
                    // Assuming requestParams.params is a Map<String, String> for multipart form data
                    val boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW" // Generate a unique boundary
                    val formData =
                        (requestParams.params as Map<String, String>).entries.joinToString(separator = "\r\n") { entry ->
                            val key = entry.key
                            val value = entry.value
                            "--$boundary\r\n" +
                                    "Content-Disposition: form-data; name=\"$key\"\r\n\r\n" +
                                    "$value\r\n"
                        } + "--$boundary--\r\n"

                    val multipartBody = HttpRequest.BodyPublishers.ofString(formData)

                    HttpRequest.newBuilder(URI(requestParams.endpoint))
                        .header("Content-Type", "multipart/form-data; boundary=$boundary")
                        .POST(multipartBody)
                        .build()
                }


//                ContentType.FORM_DATA.text -> {
//                    // Assuming requestParams.params is a Map<String, String> for multipart form data
//                    val formData = (requestParams.params as Map<String, String>).entries.stream()
//                        .map { entry -> BodyPublishers.ofString(entry.key + "=" + entry.value) }
//                        .collect(Collectors.toList())
//                    val multipartBody = BodyPublishers.newMultipartBuilder()
//                        .addAll(formData)
//                        .build()
//                    HttpRequest.newBuilder(URI(requestParams.endpoint))
//                        .header("Content-Type", contentType)
//                        .POST(multipartBody)
//                        .build()
//                }

                else -> throw IllegalArgumentException("Unsupported content type: $contentType")
            }
            return requestBody
        } catch (ex: Exception) {
            logger.error("Error occurred while building request : ${ex.message}")
            throw ServiceException(-1, ex.localizedMessage)
        }
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