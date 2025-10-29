package com.tamersarioglu.loadlogic.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Custom validation annotation for material types.
 * Validates that the material type is from the predefined list.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MaterialValidator::class])
@MustBeDocumented
annotation class ValidMaterial(
    val message: String = "Invalid material type. Valid materials are: Sand, Gravel, Concrete, Bricks, Steel, Wood",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)