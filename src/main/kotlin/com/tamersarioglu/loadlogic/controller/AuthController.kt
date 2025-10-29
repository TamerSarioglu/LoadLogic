package com.tamersarioglu.loadlogic.controller

import com.tamersarioglu.loadlogic.dto.AuthResponse
import com.tamersarioglu.loadlogic.dto.LoginRequest
import com.tamersarioglu.loadlogic.dto.RegisterRequest
import com.tamersarioglu.loadlogic.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * REST controller for user authentication operations.
 * Handles user registration and login endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"])
class AuthController(
    private val userService: UserService
) {

    /**
     * Registers a new user in the system.
     * 
     * @param registerRequest The registration request containing user details
     * @return ResponseEntity with AuthResponse containing JWT token and user info
     */
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<AuthResponse> {
        return try {
            val authResponse = userService.registerUser(registerRequest)
            ResponseEntity.status(HttpStatus.CREATED).body(authResponse)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /**
     * Authenticates a user and returns JWT token.
     * 
     * @param loginRequest The login request containing user credentials
     * @return ResponseEntity with AuthResponse containing JWT token and user info
     */
    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponse> {
        return try {
            val authResponse = userService.authenticateUser(loginRequest)
            ResponseEntity.ok(authResponse)
        } catch (e: org.springframework.security.core.AuthenticationException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}