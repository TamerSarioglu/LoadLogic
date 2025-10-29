package com.tamersarioglu.loadlogic.dto

import com.tamersarioglu.loadlogic.entity.JobStatus
import jakarta.validation.constraints.NotNull

/**
 * DTO for job status update requests.
 * Contains the new status to be applied to a job.
 */
data class UpdateJobStatusRequest(
    @field:NotNull(message = "Status is required")
    val status: JobStatus
)