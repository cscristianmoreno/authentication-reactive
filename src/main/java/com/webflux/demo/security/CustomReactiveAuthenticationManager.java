package com.webflux.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private ReactiveAuthenticationProvider reactiveAuthenticationProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return reactiveAuthenticationProvider.authenticate(authentication)
        .flatMap((auth) -> {
            return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
        });
    }
}
