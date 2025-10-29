package com.tamersarioglu.loadlogic.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Custom validation annotation for equipment types.
 * Validates that the equipment is from the predefined list.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EquipmentValidator::class])
@MustBeDocumented
annotation class ValidEquipment(
    val message: String = "Invalid equipment. Valid equipment are: Truck-01, Truck-02, Excavator-A, Loader-B, Crane-X",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)