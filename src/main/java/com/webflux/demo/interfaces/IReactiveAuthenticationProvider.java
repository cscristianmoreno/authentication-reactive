package com.webflux.demo.interfaces;

import org.springframework.security.core.Authentication;

import reactor.core.publisher.Mono;

public interface IReactiveAuthenticationProvider {
    Mono<Authentication> authenticate(Authentication authentication);
}
