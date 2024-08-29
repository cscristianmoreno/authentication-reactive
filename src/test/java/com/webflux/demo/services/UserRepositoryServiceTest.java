package com.webflux.demo.services;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.webflux.demo.entity.Users;
import com.webflux.demo.repository.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRepositoryService userRepositoryService;

    private Users users;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setup() {
        users = new Users();
        
        users.setId(1);
        users.setUsername("user");
        users.setPassword("user");
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setAge(27);
    }

    @Test
    void save() {
        // GIVEN
        given(userRepository.save(users)).willReturn(Mono.just(users));
        given(passwordEncoder.encode(users.getPassword())).willReturn(bCryptPasswordEncoder.encode(users.getPassword()));

        // WHEN 
        Mono<Users> result = userRepositoryService.save(users); 

        // THEN
        StepVerifier.create(result)
        .expectNext(users)
        .expectComplete()
        .verify();
    }

    @Test
    void update() {
        // GIVEN
        given(userRepository.save(users)).willReturn(Mono.just(users));
        
        // WHEN
        users.setAge(28);
        Mono<Users> result = userRepositoryService.save(users);

        // THEN
        StepVerifier.create(result)
        .expectNextMatches((t) -> t.getAge() == users.getAge())
        .expectComplete()
        .verify();
    }

    @Test
    void findById() {
        // GIVEN
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        given(userRepository.findById(users.getId())).willReturn(Mono.just(users));

        // WHEN
        Mono<Users> result = userRepositoryService.findById(users.getId());

        // THEN
        StepVerifier.create(result)
        .expectNext(users)
        .expectComplete()
        .verify();
    }

    @Test
    void findByIdNotExist() {
        // GIVEN
        given(userRepository.findById(users.getId())).willReturn(Mono.empty());

        // WHEN
        Mono<Users> result = userRepositoryService.findById(users.getId());

        // THEN
        StepVerifier.create(result)
        .expectNoAccessibleContext()
        .expectComplete()
        .verify();
    }

    @Test
    void findByUsername() {
        // GIVEN
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        given(userRepository.findByUsername(users.getUsername())).willReturn(Mono.just(users));

        // WHEN
        Mono<Users> result = userRepositoryService.findByUsername(users.getUsername());

        // THEN
        StepVerifier.create(result)
        .expectNext(users)
        .expectComplete()
        .verify();
    }

    @Test
    void findByUsernameNotFound() {
        // GIVEN
        given(userRepository.findByUsername(users.getUsername())).willReturn(Mono.empty());

        // WHEN
        Mono<Users> result = userRepositoryService.findByUsername(users.getUsername());

        // THEN
        StepVerifier.create(result)
        .expectNoAccessibleContext()
        .expectComplete()
        .verify();
    }

    @Test
    void deleteById() {
        // GIVEN && WHEN
        Mono<Void> result = userRepository.deleteById(users.getId());

        // THEN
        StepVerifier.create(result)
        .expectNoAccessibleContext();
    }
}
