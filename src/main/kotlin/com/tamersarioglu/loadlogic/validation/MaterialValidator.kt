package com.tamersarioglu.loadlogic.validation

import com.tamersarioglu.loadlogic.config.ReferenceDataConfig
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

/**
 * Validator implementation for ValidMaterial annotation.
 * Validates material types against the predefined list from application properties.
 */
@Component
class MaterialValidator(
    private val referenceDataConfig: ReferenceDataConfig
) : ConstraintValidator<ValidMaterial, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }
        return referenceDataConfig.materials.contains(value)
    }
}