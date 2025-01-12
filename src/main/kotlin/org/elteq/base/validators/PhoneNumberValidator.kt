package org.elteq.base.validators

import jakarta.inject.Singleton
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.elteq.base.utils.PhoneNumberUtils

@Singleton
class PhoneNumberValidator : ConstraintValidator<ValidPhoneNumber, String> {
    override fun isValid(phoneNumber: String, context: ConstraintValidatorContext): Boolean {
        return PhoneNumberUtils.isValid(phoneNumber)
    }
}
