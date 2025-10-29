package com.tamersarioglu.loadlogic.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO for job creation requests.
 * Contains all required fields for creating a new construction job.
 */
data class CreateJobRequest(
    @field:NotBlank(message = "Job title is required")
    @field:Size(max = 200, message = "Job title must not exceed 200 characters")
    val title: String,
    
    @field:NotBlank(message = "Material type is required")
    val materialType: String,
    
    @field:NotBlank(message = "Quantity is required")
    @field:Size(max = 100, message = "Quantity must not exceed 100 characters")
    val quantity: String,
    
    @field:NotBlank(message = "Destination address is required")
    @field:Size(max = 500, message = "Destination address must not exceed 500 characters")
    val destinationAddress: String,
    
    @field:NotBlank(message = "Contact person is required")
    @field:Size(max = 100, message = "Contact person must not exceed 100 characters")
    val contactPerson: String,
    
    @field:NotBlank(message = "Contact phone is required")
    @field:Size(max = 20, message = "Contact phone must not exceed 20 characters")
    val contactPhone: String,
    
    @field:NotBlank(message = "Assigned driver username is required")
    val assignedDriverUsername: String,
    
    @field:NotBlank(message = "Assigned crew username is required")
    val assignedCrewUsername: String,
    
    @field:NotBlank(message = "Assigned equipment is required")
    val assignedEquipment: String
)