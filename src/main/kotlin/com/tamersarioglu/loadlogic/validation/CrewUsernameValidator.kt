package com.tamersarioglu.loadlogic.validation

import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.service.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.stereotype.Component

/**
 * Validator implementation for ValidCrewUsername annotation.
 * Validates that the username exists and belongs to a user with CREW role.
 */
@Component
class CrewUsernameValidator(
    private val userService: UserService
) : ConstraintValidator<ValidCrewUsername, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || value.isBlank()) {
            return false
        }
        return userService.validateUserRole(value, Role.CREW)
    }
}