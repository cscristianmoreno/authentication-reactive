package com.webflux.demo.interfaces.services;

import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public interface IJwtService {
    Mono<String> encode(Authentication authentication);
}
