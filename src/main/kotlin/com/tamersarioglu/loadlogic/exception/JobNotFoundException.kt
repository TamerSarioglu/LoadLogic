package com.tamersarioglu.loadlogic.exception

/**
 * Exception thrown when a requested job is not found in the system.
 */
class JobNotFoundException(message: String) : RuntimeException(message)