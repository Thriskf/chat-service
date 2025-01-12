package org.elteq.base.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.Reader
import java.text.DateFormat
import java.text.SimpleDateFormat

object MessageSerializer {
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a z")
    val mapper = ObjectMapper().findAndRegisterModules().setDateFormat(dateFormat)

    fun <T> toJsonString(data: T): String? {
        return try {
            mapper.writeValueAsString(data)
        } catch (ex: JsonProcessingException) {
            null
        }
    }

    fun <T> fromJsonString(value: String): T? {
        return try {
            val typeRef = object: TypeReference<T>(){}
            mapper.readValue(value, typeRef)
        } catch (ex: JsonProcessingException) {
            null
        } catch (ex: JsonMappingException) {
            null
        }
    }

    fun <T> toMap(value: T): HashMap<String, Any>? {
        return try {
            val str = toJsonString(value)
            val typeRef = object : TypeReference<HashMap<String, Any>>() {}
            mapper.readValue(str, typeRef)
        } catch (ex: JsonProcessingException) {
            null
        } catch (ex: JsonMappingException) {
            null
        }
    }

    fun toJsonObject(value: Reader): JsonNode? {
        return try {
            mapper.readTree(value)
        } catch (ex: JsonProcessingException) {
            null
        } catch (ex: JsonMappingException) {
            null
        }
    }

    fun toJsonObject(value: String): JsonNode? {
        return try {
            mapper.readTree(value)
        } catch (ex: JsonProcessingException) {
            null
        } catch (ex: JsonMappingException) {
            null
        }
    }
}