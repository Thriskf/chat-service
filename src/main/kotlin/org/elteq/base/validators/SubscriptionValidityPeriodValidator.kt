package org.elteq.base.validators


import jakarta.inject.Singleton
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.elteq.base.exception.ServiceException
import org.elteq.base.utils.MessageSerializer
import java.time.LocalDateTime

@Singleton
class SubscriptionValidityPeriodValidator : ConstraintValidator<ValidSubscriptionValidityPeriod, Any> {
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val (hasNoEnding, startDate, endDate) = extractValidityPeriodDetails(value)

        if (hasNoEnding) {
            return true
        }

        val error = checkValidityPeriod(startDate, endDate)

        if (error.isNotBlank()) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate(error).addConstraintViolation()
            return false
        }

        return true
    }

    private fun extractValidityPeriodDetails(value: Any): Triple<Boolean, LocalDateTime?, LocalDateTime?> {
        val valueMap = MessageSerializer.toMap(value) ?: throw ServiceException(400, "Invalid parameter ${value::class.java}")

        val hasNoEndingParam = "hasNoEnding"
        val hasNoEnding = valueMap[hasNoEndingParam] ?: throw ServiceException(400, "Parameter $hasNoEndingParam is required in ${value::class.java}")

        val startAtParam = "startAt"
        val startAt = valueMap[startAtParam] ?: throw ServiceException(400, "Parameter $startAtParam is required in ${value::class.java}")

        val endAtParam = "endAt"
        val endAt = valueMap[endAtParam] ?: throw ServiceException(400, "Parameter $endAtParam is required in ${value::class.java}")

        return  Triple(hasNoEnding as Boolean, LocalDateTime.parse(startAt.toString()), LocalDateTime.parse(endAt.toString()))
    }

    private fun checkValidityPeriod(startDate: LocalDateTime?, endDate: LocalDateTime?): String {
        if (startDate == null) {
            return "A start date must be specified when hasNoEnding is false."
        }

        if (endDate == null) {
            return "An end date must be specified when hasNoEnding is false."
        }

        val now = LocalDateTime.now()

        if (!startDate.isAfter(now)) {
            return "Start date must be in the future."
        }

        if (!endDate.isAfter(now)) {
            return "End date must be in the future."
        }

        if (startDate.isEqual(endDate)) {
            return "Start date cannot be the same as the end date."
        }

        if (startDate.isAfter(endDate)) {
            return "Start date cannot be after the end date."
        }

        return ""
    }
}