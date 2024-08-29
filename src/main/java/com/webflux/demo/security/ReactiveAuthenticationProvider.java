package com.webflux.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.webflux.demo.interfaces.IReactiveAuthenticationProvider;
import reactor.core.publisher.Mono;

@Component
public class ReactiveAuthenticationProvider implements IReactiveAuthenticationProvider {

    @Autowired
    private CustomReactiveUserDetailsService customReactiveUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        return customReactiveUserDetailsService.findByUsername(username)
        .flatMap((user) -> {
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return Mono.error(new BadCredentialsException("Password not matches!"));
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
            );

            return Mono.just(usernamePasswordAuthenticationToken);
        });
    }
}
