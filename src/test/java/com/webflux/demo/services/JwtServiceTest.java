package com.webflux.demo.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.test.StepVerifier;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void encode() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "user",
            passwordEncoder.encode("password"),
            AuthorityUtils.createAuthorityList("ROLE_SAVE")
        );

        StepVerifier.create(jwtService.encode(usernamePasswordAuthenticationToken))
        .consumeNextWith((token) -> {
            assertNotNull(token);
        })
        .expectComplete()
        .verify();
    }
}
