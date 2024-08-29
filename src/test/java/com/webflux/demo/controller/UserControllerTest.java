package com.webflux.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.demo.entity.Authority;
import com.webflux.demo.entity.Users;
import com.webflux.demo.roles.Roles;
import com.webflux.demo.services.UserRepositoryService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class UserControllerTest {

    private WebTestClient webTestClient;

    @Autowired
    private RouterFunction<ServerResponse> userRoute;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private Users users;


    @BeforeEach
    void setup() {
        webTestClient = WebTestClient.bindToRouterFunction(userRoute).build();

        users = new Users();
        users.setUsername("user");
        users.setPassword("user");
        users.setAge(27);
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setRole(Roles.ROLE_SAVE, Roles.ROLE_READ, Roles.ROLE_DELETE);
    }   

    @Test
    void save() {
        // WHEN
       BodyContentSpec body = webTestClient
        .post()
        .uri("/users/save")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(users)
        .exchange()
        .expectBody();

        // THEN
        body.consumeWith((response) -> {
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatus());
        });
    }

    @Test
    void update() {
        
        // GIVEN 
        StepVerifier.create(userRepositoryService.save(users))
        .expectNext(users)
        .expectComplete()
        .verify();

        users.setId(1);
        users.setUsername("test");

        // THEN
        BodyContentSpec body = webTestClient
        .put()
        .uri("/users/update")
        .bodyValue(users)
        .exchange()
        .expectBody();

        // WHEN
        body
        .consumeWith((result) -> {
            assertNotNull(result);
            assertEquals(HttpStatus.OK, result.getStatus());
        });
    }

    @Test
    void findById() {
        StepVerifier.create(userRepositoryService.save(users))
        .expectNext(users)
        .expectComplete()
        .verify();
        
        // WHEN
        BodyContentSpec body = webTestClient
        .get()
        .uri("/users/{id}/", 1)
        .exchange()
        .expectBody();

        // THEN
        body.consumeWith((response) -> {
            System.out.println(response);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatus());
        });
    }

    @Test
    void findByUsername() {

        // GIVEN
        StepVerifier.create(userRepositoryService.save(users))
        .expectNext(users)
        .expectComplete()
        .verify();

        // WHEN
        BodyContentSpec body = webTestClient
        .get()
        .uri("/users/username?username={username}", users.getUsername())
        .exchange()
        .expectBody();

        // THEN
        body.consumeWith((response) -> {
            System.out.println(response);
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatus());
        });
    }

    @Test
    void deleteById() {
        // GIVEN
        StepVerifier
        .create(userRepositoryService.save(users))
        .expectNext(users)
        .expectComplete()
        .verify();

        // WHEN
        BodyContentSpec body = webTestClient
        .delete()
        .uri("/users/{id}/", 1)
        .exchange()
        .expectBody();

        // THEN
        body.consumeWith((response) -> {
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatus());
        });
    }
}
