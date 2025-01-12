package org.elteq.base.utils

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN
import jakarta.inject.Singleton

@Singleton
class PhoneNumberUtils {
    companion object {
        private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
        fun isValid(phoneNumber: String): Boolean {
            return try {

                println("phoneNumber received :: $phoneNumber")
                val number = makePhoneNumber(phoneNumber)

                println("phoneNumber after make :: $number")
                phoneNumberUtil
                    .isValidNumber(number)
            } catch (e: NumberParseException) {
                false
            }
        }

        fun resolveCountryIsoCode(phoneNumber: String): String {
            val number = makePhoneNumber(phoneNumber)
            return phoneNumberUtil
                .getRegionCodeForNumber(number)
        }

        fun resolveCountryIsoCode(number: PhoneNumber): String {
            return phoneNumberUtil
                .getRegionCodeForNumber(number)

        }

        fun makePhoneNumber(numberInString: String): PhoneNumber {
            val numberWithPlusSign = numberInString.ensureStartsWith("+")
            println("phoneNumber after appending '+' $numberWithPlusSign")

//            return PhoneNumber()
//                .setRawInput(numberWithPlusSign)
//                .setCountryCodeSource(FROM_NUMBER_WITH_PLUS_SIGN)
            return phoneNumberUtil.parse(numberWithPlusSign, null)

        }

        fun toE164(number: PhoneNumber): String {
            return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164)
        }

        fun toE164(number: String): String {
            return toE164(makePhoneNumber(number))
        }
    }
}