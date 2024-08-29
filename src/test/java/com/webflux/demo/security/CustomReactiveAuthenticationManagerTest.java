package com.webflux.demo.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class CustomReactiveAuthenticationManagerTest {

    @MockBean
    private ReactiveAuthenticationProvider reactiveAuthenticationProvider;

    @Autowired
    private CustomReactiveAuthenticationManager customReactiveAuthenticationManager;

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    @BeforeEach
    void setup() {
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "user",
            "user",
            AuthorityUtils.createAuthorityList("ROLE_USER")
        );
    }

    @Test
    void authenticate() {
        // GIVEN
        given(reactiveAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken)).willReturn(Mono.just(usernamePasswordAuthenticationToken));

        // WHEN
        Mono<Authentication> authentication = customReactiveAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // THEN
        StepVerifier.create(authentication)
        .consumeNextWith((result) -> {
            System.out.println(result);
            assertNotNull(result);
        })
        .expectComplete()
        .verify();
    }
}
