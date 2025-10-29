package com.tamersarioglu.loadlogic.service

import com.tamersarioglu.loadlogic.entity.Job
import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.exception.UnauthorizedAccessException
import org.springframework.stereotype.Service

/**
 * Service for handling authorization checks across the application.
 * Centralizes authorization logic for job access and operations.
 */
@Service
class AuthorizationService {

    /**
     * Validates that a user is authorized to access a specific job.
     * Chiefs can access any job, Drivers and Crew can only access assigned jobs.
     * 
     * @param job The job to check access for
     * @param username The username requesting access
     * @param userRole The role of the user requesting access
     * @throws UnauthorizedAccessException if user is not authorized
     */
    fun validateJobAccess(job: Job, username: String, userRole: Role) {
        // Chiefs can access any job
        if (userRole == Role.CHIEF) {
            return
        }

        // Drivers and Crew can only access jobs assigned to them
        if (!job.isAssignedTo(username)) {
            throw UnauthorizedAccessException("User '$username' is not authorized to access job ${job.id}")
        }
    }

    /**
     * Validates that a user is authorized to update a specific job.
     * Chiefs can update any job, Drivers and Crew can only update assigned jobs.
     * 
     * @param job The job to check update access for
     * @param username The username requesting update access
     * @param userRole The role of the user requesting update access
     * @throws UnauthorizedAccessException if user is not authorized
     */
    fun validateJobUpdateAccess(job: Job, username: String, userRole: Role) {
        // Use the same logic as job access for now
        validateJobAccess(job, username, userRole)
    }

    /**
     * Checks if a user has the required role for an operation.
     * 
     * @param userRole The user's current role
     * @param requiredRole The required role for the operation
     * @return true if user has the required role, false otherwise
     */
    fun hasRequiredRole(userRole: Role, requiredRole: Role): Boolean {
        return userRole == requiredRole
    }

    /**
     * Checks if a user has any of the required roles for an operation.
     * 
     * @param userRole The user's current role
     * @param requiredRoles The list of acceptable roles for the operation
     * @return true if user has any of the required roles, false otherwise
     */
    fun hasAnyRequiredRole(userRole: Role, requiredRoles: List<Role>): Boolean {
        return requiredRoles.contains(userRole)
    }
}