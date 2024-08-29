package com.webflux.demo.security;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Component
public class CustomCorsConfigurer implements WebFluxConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedMethods("*")
        .allowedOrigins("*");
    }
}
