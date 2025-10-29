package com.tamersarioglu.loadlogic.config

import com.tamersarioglu.loadlogic.entity.Role
import com.tamersarioglu.loadlogic.entity.User
import com.tamersarioglu.loadlogic.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * Data initializer component that creates sample users for development and testing.
 * Implements CommandLineRunner to execute after application startup.
 */
@Component
class DataInitializer(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    override fun run(vararg args: String?) {
        logger.info("Starting data initialization...")
        
        // Check if users already exist to avoid duplicates
        if (userRepository.count() > 0) {
            logger.info("Users already exist, skipping data initialization")
            return
        }

        createSampleUsers()
        logger.info("Data initialization completed successfully")
    }

    /**
     * Creates sample users for each role to facilitate development and testing.
     */
    private fun createSampleUsers() {
        val sampleUsers = listOf(
            // Chief users - can create and manage jobs
            User(
                username = "chief1",
                fullName = "John Smith",
                password = passwordEncoder.encode("password123"),
                role = Role.CHIEF,
                isActive = true
            ),
            User(
                username = "chief2", 
                fullName = "Sarah Johnson",
                password = passwordEncoder.encode("password123"),
                role = Role.CHIEF,
                isActive = true
            ),
            
            // Driver users - operate vehicles and equipment
            User(
                username = "driver1",
                fullName = "Mike Wilson",
                password = passwordEncoder.encode("password123"),
                role = Role.DRIVER,
                isActive = true
            ),
            User(
                username = "driver2",
                fullName = "Lisa Brown",
                password = passwordEncoder.encode("password123"),
                role = Role.DRIVER,
                isActive = true
            ),
            User(
                username = "driver3",
                fullName = "Tom Davis",
                password = passwordEncoder.encode("password123"),
                role = Role.DRIVER,
                isActive = true
            ),
            
            // Crew users - assist with loading/unloading and site work
            User(
                username = "crew1",
                fullName = "Alex Martinez",
                password = passwordEncoder.encode("password123"),
                role = Role.CREW,
                isActive = true
            ),
            User(
                username = "crew2",
                fullName = "Emma Garcia",
                password = passwordEncoder.encode("password123"),
                role = Role.CREW,
                isActive = true
            ),
            User(
                username = "crew3",
                fullName = "Ryan Thompson",
                password = passwordEncoder.encode("password123"),
                role = Role.CREW,
                isActive = true
            ),
            User(
                username = "crew4",
                fullName = "Jessica Lee",
                password = passwordEncoder.encode("password123"),
                role = Role.CREW,
                isActive = true
            )
        )

        // Save all sample users
        userRepository.saveAll(sampleUsers)
        
        logger.info("Created ${sampleUsers.size} sample users:")
        sampleUsers.forEach { user ->
            logger.info("- ${user.username} (${user.fullName}) - Role: ${user.role}")
        }
    }
}