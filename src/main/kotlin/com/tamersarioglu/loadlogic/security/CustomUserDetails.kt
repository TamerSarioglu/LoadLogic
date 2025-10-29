package com.tamersarioglu.loadlogic.security

import com.tamersarioglu.loadlogic.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * Custom implementation of Spring Security's UserDetails interface.
 * Wraps the User entity to provide authentication and authorization information.
 */
class CustomUserDetails(private val _user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_${_user.role.name}"))
    }

    override fun getPassword(): String {
        return _user.password
    }

    override fun getUsername(): String {
        return _user.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return _user.isActive
    }

    /**
     * Get the underlying User entity.
     */
    fun getUser(): User {
        return _user
    }

    /**
     * Get the user's role as a string.
     */
    fun getRole(): String {
        return _user.role.name
    }

    /**
     * Get the user's full name.
     */
    fun getFullName(): String {
        return _user.fullName
    }
}