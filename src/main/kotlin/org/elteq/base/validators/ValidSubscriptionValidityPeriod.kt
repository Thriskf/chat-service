package org.elteq.base.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import org.elteq.base.validators.SubscriptionValidityPeriodValidator
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SubscriptionValidityPeriodValidator::class])
@MustBeDocumented
annotation class ValidSubscriptionValidityPeriod(
    val message: String = "{ValidSubscriptionValidityPeriod.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)