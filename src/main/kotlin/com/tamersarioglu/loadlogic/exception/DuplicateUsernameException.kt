package com.tamersarioglu.loadlogic.exception

/**
 * Exception thrown when attempting to create a user with a username that already exists.
 */
class DuplicateUsernameException(message: String) : RuntimeException(message)