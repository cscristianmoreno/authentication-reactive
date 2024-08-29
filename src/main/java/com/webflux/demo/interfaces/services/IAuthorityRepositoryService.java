package com.webflux.demo.interfaces.services;

import com.webflux.demo.entity.Authority;
import com.webflux.demo.interfaces.ICrudRepository;

import reactor.core.publisher.Mono;

public interface IAuthorityRepositoryService extends ICrudRepository<Authority> {
    Mono<Authority> findByRole(String role); 
}
