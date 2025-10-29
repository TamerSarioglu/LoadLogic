package com.tamersarioglu.loadlogic.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

/**
 * Job entity representing construction jobs with material delivery, equipment assignment, and crew
 * coordination. Tracks job details, assignments, and status progression from creation to
 * completion.
 */
@Entity
@Table(name = "jobs")
data class Job(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
        @Column(nullable = false, length = 200)
        @field:NotBlank(message = "Job title is required")
        @field:Size(max = 200, message = "Job title must not exceed 200 characters")
        val title: String,
        @Column(nullable = false, length = 50)
        @field:NotBlank(message = "Material type is required")
        val materialType: String,
        @Column(nullable = false, length = 100)
        @field:NotBlank(message = "Quantity is required")
        val quantity: String,
        @Column(nullable = false, length = 500)
        @field:NotBlank(message = "Destination address is required")
        @field:Size(max = 500, message = "Destination address must not exceed 500 characters")
        val destinationAddress: String,
        @Column(nullable = false, length = 100)
        @field:NotBlank(message = "Contact person is required")
        @field:Size(max = 100, message = "Contact person must not exceed 100 characters")
        val contactPerson: String,
        @Column(nullable = false, length = 20)
        @field:NotBlank(message = "Contact phone is required")
        @field:Size(max = 20, message = "Contact phone must not exceed 20 characters")
        val contactPhone: String,
        @Column(nullable = false, length = 50)
        @field:NotBlank(message = "Assigned driver username is required")
        val assignedDriverUsername: String,
        @Column(nullable = false, length = 50)
        @field:NotBlank(message = "Assigned crew username is required")
        val assignedCrewUsername: String,
        @Column(nullable = false, length = 50)
        @field:NotBlank(message = "Assigned equipment is required")
        val assignedEquipment: String,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false, length = 20)
        var status: JobStatus = JobStatus.PENDING,
        @Column(nullable = false, length = 50)
        @field:NotBlank(message = "Created by chief is required")
        val createdByChief: String,
        @Column(nullable = false, updatable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false) var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    /** Updates the updatedAt timestamp when the entity is modified. */
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }

    /** Checks if the given username is assigned to this job (either as driver or crew). */
    fun isAssignedTo(username: String): Boolean {
        return assignedDriverUsername == username || assignedCrewUsername == username
    }

    /** Updates the job status and automatically updates the timestamp. */
    fun updateStatus(newStatus: JobStatus) {
        this.status = newStatus
        this.updatedAt = LocalDateTime.now()
    }
}
