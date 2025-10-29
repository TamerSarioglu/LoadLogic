package com.tamersarioglu.loadlogic.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Custom validation annotation for crew usernames.
 * Validates that the username exists and belongs to a user with CREW role.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CrewUsernameValidator::class])
@MustBeDocumented
annotation class ValidCrewUsername(
    val message: String = "Invalid crew username. User must exist and have CREW role",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)