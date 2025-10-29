package com.tamersarioglu.loadlogic.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

/**
 * User entity representing system users with role-based access control.
 * Supports CHIEF, DRIVER, and CREW roles for construction job coordination.
 */
@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["username"])
    ]
)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    
    @Column(unique = true, nullable = false, length = 50)
    @field:NotBlank(message = "Username is required")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,
    
    @Column(nullable = false, length = 100)
    @field:NotBlank(message = "Full name is required")
    @field:Size(max = 100, message = "Full name must not exceed 100 characters")
    val fullName: String,
    
    @Column(nullable = false)
    @field:NotBlank(message = "Password is required")
    val password: String,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    val role: Role,
    
    @Column(nullable = false)
    val isActive: Boolean = true,
    
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * Updates the updatedAt timestamp when the entity is modified.
     */
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}