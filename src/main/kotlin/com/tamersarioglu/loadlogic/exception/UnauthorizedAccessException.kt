package com.tamersarioglu.loadlogic.exception

/**
 * Exception thrown when a user attempts to access a resource they are not authorized to access.
 */
class UnauthorizedAccessException(message: String) : RuntimeException(message)