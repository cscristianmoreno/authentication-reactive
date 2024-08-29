package com.webflux.demo.repository;

import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.webflux.demo.entity.Authority;

import reactor.core.publisher.Mono;

@EnableR2dbcRepositories
public interface AuthorityRepository extends ReactiveCrudRepository<Authority, Integer> {
    Mono<Authority> findByRole(String role);
}
