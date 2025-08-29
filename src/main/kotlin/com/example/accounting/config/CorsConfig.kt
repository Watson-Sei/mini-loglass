package com.example.accounting.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**") // addMapping("/api/**") などで制限をする方が良い
            .allowedOrigins("*") // .allowedOrigins("http://localhost:3000") などで制限をする方が良い
            .allowedMethods("*") // .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") などで制限をする方が良い
            .allowedHeaders("*")
            .allowCredentials(false)
    }
}
