package com.tamersarioglu.loadlogic.controller

import com.tamersarioglu.loadlogic.dto.CreateJobRequest
import com.tamersarioglu.loadlogic.dto.JobResponse
import com.tamersarioglu.loadlogic.dto.UpdateJobStatusRequest
import com.tamersarioglu.loadlogic.security.CustomUserDetails
import com.tamersarioglu.loadlogic.service.JobService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

/**
 * REST controller for job management operations.
 * Handles job creation, retrieval, and status updates with role-based access control.
 */
@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = ["*"])
class JobController(
    private val jobService: JobService
) {

    /**
     * Creates a new job. Only accessible by users with CHIEF role.
     * 
     * @param createJobRequest The job creation request
     * @param authentication The authentication context containing user details
     * @return ResponseEntity with created JobResponse
     */
    @PostMapping
    @PreAuthorize("hasRole('CHIEF')")
    fun createJob(
        @Valid @RequestBody createJobRequest: CreateJobRequest,
        authentication: Authentication
    ): ResponseEntity<JobResponse> {
        return try {
            val userDetails = authentication.principal as CustomUserDetails
            val jobResponse = jobService.createJob(createJobRequest, userDetails.username)
            ResponseEntity.status(HttpStatus.CREATED).body(jobResponse)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build<JobResponse>()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build<JobResponse>()
        }
    }

    /**
     * Retrieves all jobs in the system. Only accessible by users with CHIEF role.
     * 
     * @return ResponseEntity with list of all JobResponse objects
     */
    @GetMapping
    @PreAuthorize("hasRole('CHIEF')")
    fun getAllJobs(): ResponseEntity<List<JobResponse>> {
        return try {
            val jobs = jobService.getAllJobs()
            ResponseEntity.ok(jobs)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /**
     * Retrieves jobs assigned to the current user. Accessible by DRIVER and CREW roles.
     * 
     * @param authentication The authentication context containing user details
     * @return ResponseEntity with list of assigned JobResponse objects
     */
    @GetMapping("/mine")
    @PreAuthorize("hasRole('DRIVER') or hasRole('CREW')")
    fun getMyAssignedJobs(authentication: Authentication): ResponseEntity<List<JobResponse>> {
        return try {
            val userDetails = authentication.principal as CustomUserDetails
            val jobs = jobService.getAssignedJobs(userDetails.username)
            ResponseEntity.ok(jobs)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /**
     * Retrieves a specific job by ID with authorization checks.
     * Chiefs can access any job, Drivers and Crew can only access assigned jobs.
     * 
     * @param id The job ID
     * @param authentication The authentication context containing user details
     * @return ResponseEntity with JobResponse if authorized
     */
    @GetMapping("/{id}")
    fun getJobById(
        @PathVariable id: Long,
        authentication: Authentication
    ): ResponseEntity<JobResponse> {
        return try {
            val userDetails = authentication.principal as CustomUserDetails
            val user = userDetails.getUser()
            
            val jobResponse = jobService.getJobById(id, user.username, user.role)
            
            if (jobResponse != null) {
                ResponseEntity.ok(jobResponse)
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /**
     * Updates the status of a job with authorization checks.
     * Chiefs can update any job, Drivers and Crew can only update assigned jobs.
     * 
     * @param id The job ID
     * @param updateRequest The status update request
     * @param authentication The authentication context containing user details
     * @return ResponseEntity with updated JobResponse
     */
    @PatchMapping("/{id}/status")
    fun updateJobStatus(
        @PathVariable id: Long,
        @Valid @RequestBody updateRequest: UpdateJobStatusRequest,
        authentication: Authentication
    ): ResponseEntity<JobResponse> {
        return try {
            val userDetails = authentication.principal as CustomUserDetails
            val user = userDetails.getUser()
            
            val updatedJob = jobService.updateJobStatus(id, updateRequest, user.username, user.role)
            ResponseEntity.ok(updatedJob)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}