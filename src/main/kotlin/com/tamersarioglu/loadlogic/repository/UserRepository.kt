package com.tamersarioglu.loadlogic.repository

import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository interface for User entity operations.
 * Provides data access methods for user management and authentication.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    
    /**
     * Find a user by username.
     * @param username The username to search for
     * @return User if found, null otherwise
     */
    fun findByUsername(username: String): User?
    
    /**
     * Check if a username already exists in the system.
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    fun existsByUsername(username: String): Boolean
    
    /**
     * Find all active users with a specific role.
     * @param role The role to filter by
     * @return List of active users with the specified role
     */
    fun findByRoleAndIsActiveTrue(role: Role): List<User>
    
    /**
     * Find all active users.
     * @return List of all active users
     */
    fun findByIsActiveTrue(): List<User>
}