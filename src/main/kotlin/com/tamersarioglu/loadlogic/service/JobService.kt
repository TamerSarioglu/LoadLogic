package com.tamersarioglu.loadlogic.service

import com.tamersarioglu.loadlogic.dto.CreateJobRequest
import com.tamersarioglu.loadlogic.dto.JobResponse
import com.tamersarioglu.loadlogic.dto.UpdateJobStatusRequest
import com.tamersarioglu.loadlogic.entity.Job
import com.tamersarioglu.loadlogic.entity.JobStatus
import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.repository.JobRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for job management operations.
 * Handles job creation, retrieval, status updates, and authorization checks.
 */
@Service
@Transactional
class JobService(
    private val jobRepository: JobRepository,
    private val userService: UserService,
    @param:Value("\${app.materials}") private val validMaterials: List<String>,
    @param:Value("\${app.equipment}") private val validEquipment: List<String>
) {

    /**
     * Creates a new job with assignment validation.
     * Only Chiefs can create jobs.
     * 
     * @param createJobRequest The job creation request
     * @param createdByChief The username of the chief creating the job
     * @return JobResponse containing the created job details
     * @throws IllegalArgumentException if validation fails
     */
    fun createJob(createJobRequest: CreateJobRequest, createdByChief: String): JobResponse {
        // Validate material type
        if (!validMaterials.contains(createJobRequest.materialType)) {
            throw IllegalArgumentException("Invalid material type: ${createJobRequest.materialType}. Valid materials: $validMaterials")
        }

        // Validate equipment
        if (!validEquipment.contains(createJobRequest.assignedEquipment)) {
            throw IllegalArgumentException("Invalid equipment: ${createJobRequest.assignedEquipment}. Valid equipment: $validEquipment")
        }

        // Validate assigned driver exists and has DRIVER role
        if (!userService.validateUserRole(createJobRequest.assignedDriverUsername, Role.DRIVER)) {
            throw IllegalArgumentException("Assigned driver '${createJobRequest.assignedDriverUsername}' does not exist or is not a DRIVER")
        }

        // Validate assigned crew exists and has CREW role
        if (!userService.validateUserRole(createJobRequest.assignedCrewUsername, Role.CREW)) {
            throw IllegalArgumentException("Assigned crew '${createJobRequest.assignedCrewUsername}' does not exist or is not a CREW member")
        }

        // Create job entity
        val job = Job(
            title = createJobRequest.title,
            materialType = createJobRequest.materialType,
            quantity = createJobRequest.quantity,
            destinationAddress = createJobRequest.destinationAddress,
            contactPerson = createJobRequest.contactPerson,
            contactPhone = createJobRequest.contactPhone,
            assignedDriverUsername = createJobRequest.assignedDriverUsername,
            assignedCrewUsername = createJobRequest.assignedCrewUsername,
            assignedEquipment = createJobRequest.assignedEquipment,
            status = JobStatus.PENDING,
            createdByChief = createdByChief
        )

        // Save job to database
        val savedJob = jobRepository.save(job)

        return mapToJobResponse(savedJob)
    }

    /**
     * Retrieves all jobs in the system.
     * Only Chiefs can access all jobs.
     * 
     * @return List of all jobs ordered by creation date (newest first)
     */
    @Transactional(readOnly = true)
    fun getAllJobs(): List<JobResponse> {
        return jobRepository.findAllByOrderByCreatedAtDesc()
            .map { mapToJobResponse(it) }
    }

    /**
     * Retrieves jobs assigned to a specific user.
     * Used by Drivers and Crew members to view their assigned jobs.
     * 
     * @param username The username to find assigned jobs for
     * @return List of jobs assigned to the user
     */
    @Transactional(readOnly = true)
    fun getAssignedJobs(username: String): List<JobResponse> {
        return jobRepository.findAssignedJobsByUsername(username)
            .map { mapToJobResponse(it) }
    }

    /**
     * Retrieves a specific job by ID with authorization checks.
     * Chiefs can access any job, Drivers and Crew can only access assigned jobs.
     * 
     * @param jobId The ID of the job to retrieve
     * @param requestingUsername The username of the user requesting the job
     * @param requestingUserRole The role of the user requesting the job
     * @return JobResponse if authorized, null if job not found
     * @throws IllegalAccessException if user is not authorized to access the job
     */
    @Transactional(readOnly = true)
    fun getJobById(jobId: Long, requestingUsername: String, requestingUserRole: Role): JobResponse? {
        val job = jobRepository.findById(jobId).orElse(null) ?: return null

        // Chiefs can access any job
        if (requestingUserRole == Role.CHIEF) {
            return mapToJobResponse(job)
        }

        // Drivers and Crew can only access jobs assigned to them
        if (!job.isAssignedTo(requestingUsername)) {
            throw IllegalAccessException("User '$requestingUsername' is not authorized to access job $jobId")
        }

        return mapToJobResponse(job)
    }

    /**
     * Updates the status of a job with authorization checks.
     * Chiefs can update any job, Drivers and Crew can only update assigned jobs.
     * 
     * @param jobId The ID of the job to update
     * @param updateRequest The status update request
     * @param requestingUsername The username of the user updating the job
     * @param requestingUserRole The role of the user updating the job
     * @return Updated JobResponse
     * @throws IllegalArgumentException if job not found
     * @throws IllegalAccessException if user is not authorized to update the job
     */
    fun updateJobStatus(
        jobId: Long, 
        updateRequest: UpdateJobStatusRequest, 
        requestingUsername: String, 
        requestingUserRole: Role
    ): JobResponse {
        val job = jobRepository.findById(jobId).orElseThrow {
            IllegalArgumentException("Job with ID $jobId not found")
        }

        // Chiefs can update any job
        if (requestingUserRole != Role.CHIEF) {
            // Drivers and Crew can only update jobs assigned to them
            if (!job.isAssignedTo(requestingUsername)) {
                throw IllegalAccessException("User '$requestingUsername' is not authorized to update job $jobId")
            }
        }

        // Update job status
        job.updateStatus(updateRequest.status)
        val updatedJob = jobRepository.save(job)

        return mapToJobResponse(updatedJob)
    }

    /**
     * Checks if a user is authorized to access a specific job.
     * 
     * @param jobId The ID of the job
     * @param username The username to check authorization for
     * @param userRole The role of the user
     * @return true if authorized, false otherwise
     */
    @Transactional(readOnly = true)
    fun isUserAuthorizedForJob(jobId: Long, username: String, userRole: Role): Boolean {
        val job = jobRepository.findById(jobId).orElse(null) ?: return false

        // Chiefs can access any job
        if (userRole == Role.CHIEF) {
            return true
        }

        // Drivers and Crew can only access jobs assigned to them
        return job.isAssignedTo(username)
    }

    /**
     * Maps a Job entity to JobResponse DTO.
     * 
     * @param job The job entity to map
     * @return JobResponse DTO
     */
    private fun mapToJobResponse(job: Job): JobResponse {
        return JobResponse(
            id = job.id,
            title = job.title,
            materialType = job.materialType,
            quantity = job.quantity,
            destinationAddress = job.destinationAddress,
            contactPerson = job.contactPerson,
            contactPhone = job.contactPhone,
            assignedDriverUsername = job.assignedDriverUsername,
            assignedCrewUsername = job.assignedCrewUsername,
            assignedEquipment = job.assignedEquipment,
            status = job.status,
            createdByChief = job.createdByChief,
            createdAt = job.createdAt,
            updatedAt = job.updatedAt
        )
    }
}