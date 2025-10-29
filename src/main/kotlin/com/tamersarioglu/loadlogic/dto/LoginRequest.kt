package com.tamersarioglu.loadlogic.dto

import jakarta.validation.constraints.NotBlank

/**
 * DTO for user login requests.
 * Contains credentials required for user authentication.
 */
data class LoginRequest(
    @field:NotBlank(message = "Username is required")
    val username: String,
    
    @field:NotBlank(message = "Password is required")
    val password: String
)