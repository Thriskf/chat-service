package org.elteq.base.utils

import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class Encryption {
    val base64Key = "Y2RiNGRlNDA0NDYwZTcyNjZmMzdmM2FiOGE4YzE1NDk="
    val iv = "xssVF4BQ7vPjb7Ud"

    fun decrypt(cipherText: String): String {
        try {
//            decrypt cipher text to byte array
            val ciphertext = Base64.getDecoder().decode(cipherText)

//            decrypt base64Key text to byte array
            val keyBytes = Base64.getDecoder().decode(base64Key)

//            convert keybytes to Aes key
            val secretKey = SecretKeySpec(keyBytes, "AES")

            val ivBytes = iv.toByteArray(StandardCharsets.UTF_8)

            val decryptedData = decryptData(ciphertext, secretKey, ivBytes)
            return String(decryptedData, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    private fun decryptData(ciphertext: ByteArray, key: SecretKey, iv: ByteArray): ByteArray {

//        encryption mode

//        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")


//        algorithm parameter spec
//        val gcmSpec = GCMParameterSpec(128, iv) // 128-bit authentication tag length
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)

        return cipher.doFinal(ciphertext)


    }

    fun encrypt(plainText: String): String {
        try {
            val keyBytes = Base64.getDecoder().decode(base64Key)
            val secretKey = SecretKeySpec(keyBytes, "AES")

            val ivBytes = iv.toByteArray(StandardCharsets.UTF_8)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val ivSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

            val encryptedBytes = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))

            return Base64.getEncoder().encodeToString(encryptedBytes)

        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}




