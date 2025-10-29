package com.tamersarioglu.loadlogic.entity

/**
 * Enum representing the status of a job in the LoadLogic system.
 * Tracks job progression from creation to completion or cancellation.
 */
enum class JobStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}