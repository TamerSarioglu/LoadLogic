package com.tamersarioglu.loadlogic.dto

import com.tamersarioglu.loadlogic.entity.Role

/**
 * DTO for available user responses.
 * Contains user information for job assignment dropdowns.
 */
data class AvailableUserResponse(
    val username: String,
    val fullName: String,
    val role: Role
)