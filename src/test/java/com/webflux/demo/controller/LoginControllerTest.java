package com.webflux.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.demo.dto.LoginDTO;
import com.webflux.demo.entity.Users;
import com.webflux.demo.services.UserRepositoryService;

import io.r2dbc.spi.ConnectionFactory;
import reactor.core.publisher.Mono;

@SpringBootTest
public class LoginControllerTest {

    @MockBean
    private UserRepositoryService userRepositoryService;

    @Autowired
    private RouterFunction<ServerResponse> loginRoute;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private WebTestClient webTestClient;


    private Users users;
    
    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToRouterFunction(loginRoute).build();

        users = new Users();
        users.setUsername("user");
        users.setPassword(passwordEncoder.encode("user"));
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setAge(27);
    }

    @Test
    void testLogin() {
        // GIVEN
        given(userRepositoryService.findByUsername("user"))
        .willReturn(Mono.just(users));

        // WHEN 
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("user");
        loginDTO.setPassword("user");

        BodyContentSpec body = webTestClient
        .post()
        .uri("/auth/login")
        .bodyValue(loginDTO)
        .exchange()
        .expectBody();

        // THEN
        body.consumeWith((response) -> {
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatus());
        });
    }
}
