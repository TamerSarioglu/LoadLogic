package com.tamersarioglu.loadlogic.service

import com.tamersarioglu.loadlogic.dto.AuthResponse
import com.tamersarioglu.loadlogic.dto.LoginRequest
import com.tamersarioglu.loadlogic.dto.RegisterRequest
import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.entity.User
import com.tamersarioglu.loadlogic.exception.DuplicateUsernameException
import com.tamersarioglu.loadlogic.exception.UserNotFoundException
import com.tamersarioglu.loadlogic.repository.UserRepository
import com.tamersarioglu.loadlogic.security.CustomUserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Service class for user management and authentication operations.
 * Handles user registration, authentication, and user lookup for job assignments.
 */
@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {

    /**
     * Registers a new user in the system with validation and password encoding.
     * 
     * @param registerRequest The registration request containing user details
     * @return AuthResponse with JWT token and user information
     */
    fun registerUser(registerRequest: RegisterRequest): AuthResponse {
        // Create new user with encoded password
        val user = User(
            username = registerRequest.username,
            fullName = registerRequest.fullName,
            password = passwordEncoder.encode(registerRequest.password),
            role = registerRequest.role,
            isActive = true
        )

        // Save user to database
        val savedUser = userRepository.save(user)

        // Generate JWT token
        val userDetails = CustomUserDetails(savedUser)
        val token = jwtService.generateToken(userDetails)

        return AuthResponse(
            token = token,
            username = savedUser.username,
            role = savedUser.role,
            fullName = savedUser.fullName
        )
    }

    /**
     * Authenticates a user and generates JWT token.
     * 
     * @param loginRequest The login request containing credentials
     * @return AuthResponse with JWT token and user information
     * @throws org.springframework.security.core.AuthenticationException if authentication fails
     */
    fun authenticateUser(loginRequest: LoginRequest): AuthResponse {
        // Authenticate user credentials
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )

        // Get user details from authentication
        val userDetails = authentication.principal as CustomUserDetails
        val user = userDetails.getUser()

        // Generate JWT token
        val token = jwtService.generateToken(userDetails)

        return AuthResponse(
            token = token,
            username = user.username,
            role = user.role,
            fullName = user.fullName
        )
    }

    /**
     * Finds a user by username for job assignment validation.
     * 
     * @param username The username to search for
     * @return User if found, null otherwise
     */
    @Transactional(readOnly = true)
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    /**
     * Validates that a user exists and has the specified role.
     * Used for job assignment validation.
     * 
     * @param username The username to validate
     * @param expectedRole The expected role for the user
     * @return true if user exists and has the expected role, false otherwise
     */
    @Transactional(readOnly = true)
    fun validateUserRole(username: String, expectedRole: Role): Boolean {
        val user = userRepository.findByUsername(username)
        return user != null && user.isActive && user.role == expectedRole
    }

    /**
     * Gets all active users with a specific role.
     * Used for finding available users for job assignments.
     * 
     * @param role The role to filter by
     * @return List of active users with the specified role
     */
    @Transactional(readOnly = true)
    fun getActiveUsersByRole(role: Role): List<User> {
        return userRepository.findByRoleAndIsActiveTrue(role)
    }

    /**
     * Gets all active users in the system.
     * 
     * @return List of all active users
     */
    @Transactional(readOnly = true)
    fun getAllActiveUsers(): List<User> {
        return userRepository.findByIsActiveTrue()
    }

    /**
     * Checks if a username exists in the system.
     * 
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    @Transactional(readOnly = true)
    fun usernameExists(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }
}