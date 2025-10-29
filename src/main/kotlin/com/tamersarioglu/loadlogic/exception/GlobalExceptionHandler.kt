package com.tamersarioglu.loadlogic.exception

import com.tamersarioglu.loadlogic.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Global exception handler for the LoadLogic application.
 * Provides centralized error handling with consistent error response formatting.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handles user not found exceptions.
     */
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(
        ex: UserNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "User Not Found",
            message = ex.message ?: "The requested user was not found",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    /**
     * Handles job not found exceptions.
     */
    @ExceptionHandler(JobNotFoundException::class)
    fun handleJobNotFoundException(
        ex: JobNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Job Not Found",
            message = ex.message ?: "The requested job was not found",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    /**
     * Handles unauthorized access exceptions.
     */
    @ExceptionHandler(UnauthorizedAccessException::class)
    fun handleUnauthorizedAccessException(
        ex: UnauthorizedAccessException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            error = "Access Denied",
            message = ex.message ?: "You are not authorized to access this resource",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse)
    }

    /**
     * Handles invalid job assignment exceptions.
     */
    @ExceptionHandler(InvalidJobAssignmentException::class)
    fun handleInvalidJobAssignmentException(
        ex: InvalidJobAssignmentException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Invalid Job Assignment",
            message = ex.message ?: "Job assignment validation failed",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * Handles duplicate username exceptions.
     */
    @ExceptionHandler(DuplicateUsernameException::class)
    fun handleDuplicateUsernameException(
        ex: DuplicateUsernameException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            error = "Duplicate Username",
            message = ex.message ?: "Username already exists",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    /**
     * Handles invalid reference data exceptions.
     */
    @ExceptionHandler(InvalidReferenceDataException::class)
    fun handleInvalidReferenceDataException(
        ex: InvalidReferenceDataException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Invalid Reference Data",
            message = ex.message ?: "Invalid reference data provided",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * Handles Spring Security authentication exceptions.
     */
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        ex: AuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = "Authentication Failed",
            message = "Invalid credentials provided",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    /**
     * Handles Spring Security access denied exceptions.
     */
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(
        ex: AccessDeniedException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            error = "Access Denied",
            message = "You do not have permission to access this resource",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse)
    }

    /**
     * Handles validation errors from @Valid annotations.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val fieldErrors = ex.bindingResult.allErrors.map { error ->
            when (error) {
                is FieldError -> "${error.field}: ${error.defaultMessage}"
                else -> error.defaultMessage ?: "Validation error"
            }
        }

        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Validation Failed",
            message = "Input validation failed",
            path = request.requestURI,
            details = fieldErrors
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * Handles illegal argument exceptions (business logic violations).
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = ex.message ?: "Invalid request parameters",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /**
     * Handles illegal access exceptions (authorization violations).
     */
    @ExceptionHandler(IllegalAccessException::class)
    fun handleIllegalAccessException(
        ex: IllegalAccessException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.FORBIDDEN.value(),
            error = "Access Denied",
            message = ex.message ?: "You are not authorized to perform this action",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse)
    }

    /**
     * Handles all other unexpected exceptions.
     */
    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        // Log the exception for debugging purposes (in production, use proper logging)
        println("Unexpected error: ${ex.message}")
        
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = "An unexpected error occurred",
            path = request.requestURI
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}