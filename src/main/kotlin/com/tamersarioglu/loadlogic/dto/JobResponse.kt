package com.tamersarioglu.loadlogic.dto

import com.tamersarioglu.loadlogic.entity.JobStatus
import java.time.LocalDateTime

/**
 * DTO for job responses.
 * Contains all job information returned in API responses.
 */
data class JobResponse(
    val id: Long,
    val title: String,
    val materialType: String,
    val quantity: String,
    val destinationAddress: String,
    val contactPerson: String,
    val contactPhone: String,
    val assignedDriverUsername: String,
    val assignedCrewUsername: String,
    val assignedEquipment: String,
    val status: JobStatus,
    val createdByChief: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)