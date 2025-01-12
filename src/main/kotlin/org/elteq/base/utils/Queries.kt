package org.elteq.base.utils

object Queries {
    fun contains(columnName: String, placeholder: String): String =
        "$columnName LIKE CONCAT('%', CONCAT(:${placeholder}, '%'))"

    fun isEqualTo(columnName: String, placeholder: String): String = "$columnName = :$placeholder"

    fun isNotEqualTo(columnName: String, placeholder: String): String = "$columnName != :$placeholder"

    fun startsWith(columnName: String, placeholder: String): String = "$columnName LIKE CONCAT(:${placeholder},'%'))"

    fun endsWith(columnName: String, placeholder: String): String = "$columnName LIKE CONCAT('%', :${placeholder}))"

    fun isIn(collectionFieldName: String, columnName: String, placeholder: String): String =
        "ELEMENT($collectionFieldName).$columnName  = :${placeholder}"

    fun isGreaterThan(columnName: String, placeholder: String): String = "$columnName > :$placeholder"

    fun isGreaterThanOrEqualTo(columnName: String, placeholder: String): String = "$columnName >= :$placeholder"

    fun isLessThan(columnName: String, placeholder: String): String = "$columnName < :$placeholder"

    fun isLessThanOrEqualTo(columnName: String, placeholder: String): String = "$columnName <= :$placeholder"
}