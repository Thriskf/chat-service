package org.elteq.base.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UserInputValidator::class])
@MustBeDocumented
annotation class ValidUserInput(
    val message: String = "Invalid Input",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)


