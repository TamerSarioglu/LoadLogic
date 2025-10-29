package com.tamersarioglu.loadlogic.dto

import java.time.LocalDateTime

/**
 * Standard error response format for API errors.
 * Provides consistent error information across all endpoints.
 */
data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
    val details: List<String>? = null
)