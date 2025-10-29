package com.tamersarioglu.loadlogic.exception

/**
 * Exception thrown when a requested user is not found in the system.
 */
class UserNotFoundException(message: String) : RuntimeException(message)