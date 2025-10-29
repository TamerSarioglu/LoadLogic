package com.tamersarioglu.loadlogic.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * Validator implementation for ValidMaterial annotation.
 * Validates material types against the predefined list from application properties.
 */
@Component
class MaterialValidator : ConstraintValidator<ValidMaterial, String> {

    @Value("\${app.materials}")
    private lateinit var validMaterials: List<String>

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }
        return validMaterials.contains(value)
    }
}