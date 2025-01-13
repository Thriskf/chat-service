package org.elteq.base.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import org.elteq.base.validators.PhoneNumberValidator
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PhoneNumberValidator::class])
@MustBeDocumented
annotation class ValidPhoneNumber(
    val message: String = "Invalid phone number",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ArrayPhoneNumberValidator::class])
@MustBeDocumented
annotation class ValidateArrayPhoneNumber(
    val message: String = "Invalid phone number",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)