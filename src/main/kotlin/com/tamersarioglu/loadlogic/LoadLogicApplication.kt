package com.tamersarioglu.loadlogic

import com.tamersarioglu.loadlogic.config.ReferenceDataConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ReferenceDataConfig::class)
class LoadLogicApplication

fun main(args: Array<String>) {
	runApplication<LoadLogicApplication>(*args)
}
