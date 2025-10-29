package com.tamersarioglu.loadlogic.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Validator implementation for ValidEquipment annotation.
 * Validates equipment against the predefined list from application properties.
 */
@Component
class EquipmentValidator : ConstraintValidator<ValidEquipment, String> {

    @Value("\${app.equipment}")
    private lateinit var validEquipment: List<String>

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }
        return validEquipment.contains(value)
    }
}