package com.webflux.demo.interfaces.services;

import com.webflux.demo.dto.LoginDTO;

import reactor.core.publisher.Mono;

public interface IAuthenticationService {
    Mono<String> authenticate(LoginDTO loginDTO); 
}
