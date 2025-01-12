//package com.generis.base.httpclient
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.generis.commons.exceptions.ServiceException
//import com.generis.sdks.httpClient.utils.MessageSerializer
//import jakarta.enterprise.context.ApplicationScoped
//import okhttp3.*
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.RequestBody.Companion.toRequestBody
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import java.io.IOException
//
////import okhttp3.MediaType.parse
////import okhttp3.RequestBody.create
//@ApplicationScoped
//class HttpClientImpl : HttpClient {
//
//    private val logger: Logger = LoggerFactory.getLogger(this::class.java)
//    private val objectMapper = ObjectMapper()
//
//    override fun get(config: RequestConfig): Response {
//        val request = buildRequest(config, HttpMethod.POST.text, null)
//
//        return executeRequest(request)
//    }
//
//    override fun postJson(config: RequestConfig, contentType: String): Response {
//
//
////        val requestBody = makeBody(config.params, ContentType.JSON.text)
////
////        val request = buildRequest(config, HttpMethod.POST.text, requestBody)
//
//        val red = Request.Builder().url(config.endpoint)
//            .method(HttpMethod.POST.text, config.params.toString().toRequestBody(contentType.toMediaType()))
//
//        return executeRequest(red.build())
//    }
//
//    override fun postXml(config: RequestConfig, contentType: String): Response {
//        val requestBody = makeBody(config.params, ContentType.XML.text)
//        val request = buildRequest(config, HttpMethod.POST.text, requestBody)
//
//        return executeRequest(request)
//    }
//
//
//    override fun postString(config: RequestConfig, contentType: String): Response {
//        val requestBody = makeBody(config.params, ContentType.TEXT.text)
//        val request = buildRequest(config, HttpMethod.POST.text, requestBody)
//
//        return executeRequest(request)
//    }
//
//    override fun postUrlEncoded(config: RequestConfig, contentType: String): Response {
//        val requestBody = makeBody(config.params, ContentType.URL_ENCODED.text)
//        val request = buildRequest(config, HttpMethod.POST.text, requestBody)
//
//        return executeRequest(request)
//    }
//
//    override fun postFormData(config: RequestConfig, contentType: String): Response {
//        val requestBody = makeBody(config.params, ContentType.FORM_DATA.text)
//        val request = buildRequest(config, HttpMethod.POST.text, requestBody)
//
//        return executeRequest(request)
//    }
//
//    private fun buildRequest(
//        config: RequestConfig,
//        method: String,
//        requestBody: RequestBody?,
//    ): Request {
//
//        val builder = Request.Builder().url(config.endpoint).method(method, requestBody)
//        config.headers!!.forEach { (key, value) -> builder.addHeader(key, value) }
//        return builder.build()
//    }
//
//    private fun makeBody(params: Any?, contentType: String): RequestBody {
//        if (contentType.lowercase().contains("multipart")) {
//            val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
//
//            if (params is Map<*, *>) {
//                params.forEach {
//                    multipartBuilder.addFormDataPart(it.key.toString(), it.value.toString())
//                }
//            }
//
//            return multipartBuilder.build()
//        } else {
//            val bodyStr: String = if (params is String) {
//                params
//            } else if (params != null) {
//                MessageSerializer.toJsonString(params) ?: ""
//            } else {
//                ""
//            }
//
//            logger.info("Body string $bodyStr")
//
//            return bodyStr.toRequestBody(contentType.toMediaTypeOrNull())
//        }
//    }
//
//    private fun executeRequest(request: Request): Response {
//
//        try {
//            val client = OkHttpClient.Builder().build()
//            val response: Response = client.newCall(request).execute()
//
//            if (!response.isSuccessful) {
//                throw IOException("Unexpected code $response")
//            }
//
//            val responseStatus = response.code
//
//            logger.info("response received ::: => $responseStatus")
//
//            if (response.code in 200..299) {
//                return response
//            }
//
//            throw ServiceException(-5, response.message)
//        } catch (ex: IOException) {
//            throw ServiceException(-5, ex.localizedMessage)
//        }
//    }
//
//
//}