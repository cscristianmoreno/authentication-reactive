package com.webflux.demo.interfaces.services;

import com.webflux.demo.entity.Users;
import com.webflux.demo.interfaces.ICrudRepository;
import reactor.core.publisher.Mono;

public interface IUserRepositoryService extends ICrudRepository<Users> {
    Mono<Users> findByUsername(String username);
}
