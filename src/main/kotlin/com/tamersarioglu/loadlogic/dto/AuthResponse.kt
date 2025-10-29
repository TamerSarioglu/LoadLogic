package com.tamersarioglu.loadlogic.dto

import com.tamersarioglu.loadlogic.entity.Role

/**
 * DTO for authentication responses.
 * Contains JWT token and user information returned after successful authentication.
 */
data class AuthResponse(
    val token: String,
    val username: String,
    val role: Role,
    val fullName: String
)