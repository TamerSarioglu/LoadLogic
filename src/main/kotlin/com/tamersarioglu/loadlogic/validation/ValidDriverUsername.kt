package com.tamersarioglu.loadlogic.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Custom validation annotation for driver usernames.
 * Validates that the username exists and belongs to a user with DRIVER role.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [DriverUsernameValidator::class])
@MustBeDocumented
annotation class ValidDriverUsername(
    val message: String = "Invalid driver username. User must exist and have DRIVER role",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)