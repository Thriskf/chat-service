package org.elteq.base.validators
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.full.memberProperties


class UserInputValidator : ConstraintValidator<ValidUserInput, Any> {

    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value != null) {
            val classValidator = ClassValidator(value)
            return classValidator.isValid(context)
        }
//        return true // Consider null values as valid, you can change this based on your requirements
        return false
    }

    private class ClassValidator(private val instance: Any) {
        fun isValid(context: ConstraintValidatorContext): Boolean {
            val customRegexPattern = Regex("[^<>*#%;$^()|]+")

            instance::class.memberProperties.forEach { property ->
                val fieldValue = property.call(instance)?.toString()

                if (fieldValue != null && !customRegexPattern.matches(fieldValue)) {
                    context.buildConstraintViolationWithTemplate("Invalid User Input: $fieldValue")
                            .addConstraintViolation()
                    return false
                }

            }

            // Additional class-level validation logic if needed
            return true
        }
    }


}


