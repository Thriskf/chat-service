package org.elteq.base.utils

fun String.ensureStartsWith(prefix: String): String {
    return if (this.startsWith(prefix)) this else prefix + this
}