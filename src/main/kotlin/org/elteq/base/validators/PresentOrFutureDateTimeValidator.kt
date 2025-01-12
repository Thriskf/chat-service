package org.elteq.base.validators

import jakarta.inject.Singleton
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.elteq.base.validators.PresentOrFutureDateTime
import java.time.LocalDateTime

@Singleton
class PresentOrFutureDateTimeValidator : ConstraintValidator<PresentOrFutureDateTime, LocalDateTime> {
    override fun isValid(date: LocalDateTime, context: ConstraintValidatorContext?): Boolean {
        return date.isAfter(LocalDateTime.now())
    }
}
