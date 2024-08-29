package com.webflux.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.webflux.demo.dto.LoginDTO;
import com.webflux.demo.interfaces.services.IAuthenticationService;
import com.webflux.demo.security.CustomReactiveAuthenticationManager;

import reactor.core.publisher.Mono;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private CustomReactiveAuthenticationManager customReactiveAuthenticationManager;

    @Autowired
    private JwtService jwtService;

    @Override
    public Mono<String> authenticate(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
        new UsernamePasswordAuthenticationToken(
            loginDTO.getUsername(),
            loginDTO.getPassword()
        );

        return customReactiveAuthenticationManager.authenticate(usernamePasswordAuthenticationToken)
        .flatMap((authentication) -> {
            System.out.println(authentication);
            return jwtService.encode(authentication);
        });
    }
    
}
