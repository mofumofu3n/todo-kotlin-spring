package com.mofumofu.todoapp.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.Formatter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Configuration
class WebConfig : WebMvcConfigurer {
    
    override fun addFormatters(registry: FormatterRegistry) {
        registry.addFormatter(LocalDateFormatter())
    }
    
    class LocalDateFormatter : Formatter<LocalDate> {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        
        override fun parse(text: String, locale: Locale): LocalDate {
            println("DEBUG: Parsing date string: '$text'")
            return if (text.isBlank()) {
                throw IllegalArgumentException("Date string is blank")
            } else {
                try {
                    val result = LocalDate.parse(text, formatter)
                    println("DEBUG: Successfully parsed date: $result")
                    result
                } catch (e: Exception) {
                    println("DEBUG: Failed to parse date '$text': ${e.message}")
                    throw e
                }
            }
        }
        
        override fun print(date: LocalDate, locale: Locale): String {
            return date.format(formatter)
        }
    }
}
