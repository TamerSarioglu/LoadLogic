package com.tamersarioglu.loadlogic.dto

import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.validation.UniqueUsername
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

/**
 * DTO for user registration requests.
 * Contains all required fields for creating a new user account.
 */
data class RegisterRequest(
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @field:UniqueUsername
    val username: String,
    
    @field:NotBlank(message = "Full name is required")
    @field:Size(max = 100, message = "Full name must not exceed 100 characters")
    val fullName: String,
    
    @field:NotBlank(message = "Password is required")
    @field:Size(min = 6, message = "Password must be at least 6 characters")
    val password: String,
    
    @field:NotNull(message = "Role is required")
    val role: Role
)