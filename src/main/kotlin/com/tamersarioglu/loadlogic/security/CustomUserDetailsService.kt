package com.tamersarioglu.LoadLogic.security

import com.tamersarioglu.LoadLogic.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * Custom implementation of Spring Security's UserDetailsService.
 * Loads user-specific data for authentication and authorization.
 */
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    /**
     * Loads user by username for Spring Security authentication.
     * @param username The username to load
     * @return UserDetails implementation containing user information
     * @throws UsernameNotFoundException if user is not found
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        
        return CustomUserDetails(user)
    }
}