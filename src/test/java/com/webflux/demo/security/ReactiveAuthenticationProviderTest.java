package com.webflux.demo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webflux.demo.entity.Users;
import com.webflux.demo.repository.UserRepository;
import com.webflux.demo.services.UserRepositoryService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class ReactiveAuthenticationProviderTest {
    
    @Autowired
    private ReactiveAuthenticationProvider reactiveAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepositoryService userRepositoryService;


    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
    private Users users;

    @BeforeEach
    void setup() {
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "user",
            "user",
            AuthorityUtils.createAuthorityList("ROLE_READ")
        );

        users = new Users();
        users.setUsername("user");
        users.setPassword(passwordEncoder.encode("user"));
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setAge(27);
    }

    @Test
    void authenticate() {
        // GIVEN
        given(userRepositoryService.findByUsername(users.getUsername())).willReturn(Mono.just(users));
        
        // WHEN
        Mono<Authentication> authentication = reactiveAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        
        // THEN
        StepVerifier.create(authentication)
        .consumeNextWith((result) -> {
            System.out.println(result.getCredentials().toString());
            assertNotNull(result);
        })
        .expectComplete()
        .verify();
    }

    @Test
    void authenticationFailed() {
        // GIVEN
        users.setPassword(passwordEncoder.encode("usersito"));
        given(userRepositoryService.findByUsername(users.getUsername())).willReturn(Mono.just(users));

        // WHEN
        Mono<Authentication> authentication = reactiveAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        // THEN
        StepVerifier.create(authentication)
        .expectError(BadCredentialsException.class)
        .verify();
    }

    @Test
    void authenticationWithUsernameNotFound() {
        // GIVEN 
        given(userRepositoryService.findByUsername(users.getUsername())).willReturn(Mono.empty());
        
        // WHEN
        Mono<Authentication> authentication = reactiveAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        // THEN
        StepVerifier.create(authentication)
        .expectError(UsernameNotFoundException.class)
        .verify();
    }
}
