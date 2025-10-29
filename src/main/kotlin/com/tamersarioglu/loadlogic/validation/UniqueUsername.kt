package com.tamersarioglu.loadlogic.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Custom validation annotation for unique usernames.
 * Validates that the username does not already exist in the system.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUsernameValidator::class])
@MustBeDocumented
annotation class UniqueUsername(
    val message: String = "Username already exists",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)