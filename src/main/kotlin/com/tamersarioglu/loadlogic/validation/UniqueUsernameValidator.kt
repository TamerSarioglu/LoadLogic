package com.tamersarioglu.loadlogic.validation

import com.tamersarioglu.loadlogic.service.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

/**
 * Validator implementation for UniqueUsername annotation.
 * Validates that the username does not already exist in the system.
 */
@Component
class UniqueUsernameValidator(
    private val userService: UserService
) : ConstraintValidator<UniqueUsername, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || value.isBlank()) {
            return true // Let @NotBlank handle null/blank validation
        }
        return !userService.usernameExists(value)
    }
}