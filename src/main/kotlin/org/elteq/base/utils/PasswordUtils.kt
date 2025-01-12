package org.elteq.base.utils

import org.apache.commons.codec.digest.DigestUtils

class PasswordUtils {
    companion object {
        private fun generateRandomPassword(length: Int): String {
            val charPool: List<Char> = ('A'..'Z') + ('a'..'z') + ('0'..'9') + listOf('!', '@', '#', '$', '%', '&', '*')
            return (1..length)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
        }

        fun hashPassword(password: String): String {
            return DigestUtils.sha512Hex(password)
        }

        fun generateTemporaryPassword(): String {
            return generateRandomPassword(8)
        }
    }
}