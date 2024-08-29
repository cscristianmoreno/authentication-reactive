package com.webflux.demo.services;

import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.webflux.demo.entity.Authority;
import com.webflux.demo.repository.AuthorityRepository;
import com.webflux.demo.roles.Roles;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class AuthorityRepositoryServiceTest {

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private AuthorityRepositoryService authorityRepositoryService;

    private Authority authority;

    @BeforeEach
    void setup() {
        authority = new Authority();
        authority.setId(1);
        authority.setRole(Roles.ROLE_SAVE);
    }

    @Test
    void testSave() {
        // GIVEN
        given(authorityRepository.save(authority)).willReturn(Mono.just(authority));

        // WHEN
        Mono<Authority> result = authorityRepositoryService.save(authority);

        // THEN
        StepVerifier.create(result)
        .expectNext(authority)
        .expectComplete()
        .verify();
    }

    @Test
    void testUpdate() {
        // GIVEN
        given(authorityRepository.save(authority)).willReturn(Mono.just(authority));
        
        // WHEN
        Mono<Authority> result = authorityRepositoryService.save(authority);

        // THEN
        StepVerifier.create(result)
        .expectNextMatches((role) -> !role.getRole().equals(authority.getRole()))
        .expectComplete()
        .verify();
    }

    @Test
    void testFindById() {
        // GIVEN
        given(authorityRepository.findById(authority.getId())).willReturn(Mono.just(authority));

        // WHEN
        Mono<Authority> result = authorityRepositoryService.findById(authority.getId());

        // THEN
        StepVerifier.create(result)
        .expectNext(authority)
        .expectComplete()
        .verify();
    }

    @Test
    void testFindByRole() {
        // GIVEN
        given(authorityRepository.findByRole(authority.getRole().name())).willReturn(Mono.just(authority));

        // WHEN
        Mono<Authority> result = authorityRepositoryService.findByRole(authority.getRole().name());

        // THEN
        StepVerifier.create(result)
        .expectNext(authority)
        .expectComplete()
        .verify();
    }

    @Test
    void testDeleteById() {
        // WHEN && THEN
        Mono<Void> result = authorityRepositoryService.deleteById(authority.getId());

        // THEN
        StepVerifier.create(result)
        .expectNoAccessibleContext();
    }
}
