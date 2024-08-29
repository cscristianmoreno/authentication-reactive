package com.webflux.demo.interfaces.controller;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface IUserController {
    Mono<ServerResponse> save(ServerRequest request);

    Mono<ServerResponse> update(ServerRequest request);

    Mono<ServerResponse> findById(ServerRequest request);

    Mono<ServerResponse> findByUsername(ServerRequest request);

    Mono<ServerResponse> deleteById(ServerRequest request);

    Mono<ServerResponse> accessOnlyAdmin(ServerRequest request);

    Mono<ServerResponse> accessOnlyUser(ServerRequest request);
}
