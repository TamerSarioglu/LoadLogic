package com.tamersarioglu.loadlogic.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for reference data (materials and equipment).
 * Binds application properties to strongly-typed configuration objects.
 */
@Configuration
@ConfigurationProperties(prefix = "app")
data class ReferenceDataConfig(
    var materials: List<String> = emptyList(),
    var equipment: List<String> = emptyList()
)