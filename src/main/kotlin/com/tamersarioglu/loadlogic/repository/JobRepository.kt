package com.tamersarioglu.loadlogic.repository

import com.tamersarioglu.loadlogic.entity.Job
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Repository interface for Job entity operations.
 * Provides data access methods for job management and assignment queries.
 */
@Repository
interface JobRepository : JpaRepository<Job, Long> {
    
    /**
     * Find all jobs ordered by creation date with newest first.
     * Used by Chiefs to view all jobs in the system.
     * @return List of all jobs ordered by creation date descending
     */
    fun findAllByOrderByCreatedAtDesc(): List<Job>
    
    /**
     * Find jobs assigned to a specific driver or crew member.
     * Used by Drivers and Crew members to view their assigned jobs.
     * @param driverUsername The driver username to search for
     * @param crewUsername The crew username to search for
     * @return List of jobs where the user is assigned as driver or crew
     */
    fun findByAssignedDriverUsernameOrAssignedCrewUsername(
        driverUsername: String, 
        crewUsername: String
    ): List<Job>
    
    /**
     * Find jobs assigned to a specific driver.
     * @param driverUsername The driver username to search for
     * @return List of jobs assigned to the specified driver
     */
    fun findByAssignedDriverUsername(driverUsername: String): List<Job>
    
    /**
     * Find jobs assigned to a specific crew member.
     * @param crewUsername The crew username to search for
     * @return List of jobs assigned to the specified crew member
     */
    fun findByAssignedCrewUsername(crewUsername: String): List<Job>
    
    /**
     * Find jobs assigned to a user (either as driver or crew) ordered by creation date.
     * @param username The username to search for in both driver and crew assignments
     * @return List of assigned jobs ordered by creation date descending
     */
    @Query("SELECT j FROM Job j WHERE j.assignedDriverUsername = :username OR j.assignedCrewUsername = :username ORDER BY j.createdAt DESC")
    fun findAssignedJobsByUsername(@Param("username") username: String): List<Job>
    
    /**
     * Find jobs created by a specific chief.
     * @param chiefUsername The chief username who created the jobs
     * @return List of jobs created by the specified chief
     */
    fun findByCreatedByChief(chiefUsername: String): List<Job>
}