package com.tamersarioglu.loadlogic.validation

import com.tamersarioglu.loadlogic.config.ReferenceDataConfig
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

/**
 * Validator implementation for ValidEquipment annotation.
 * Validates equipment against the predefined list from application properties.
 */
@Component
class EquipmentValidator(
    private val referenceDataConfig: ReferenceDataConfig
) : ConstraintValidator<ValidEquipment, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return false
        }
        return referenceDataConfig.equipment.contains(value)
    }
}