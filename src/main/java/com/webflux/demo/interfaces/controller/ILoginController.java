package com.webflux.demo.interfaces.controller;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public interface ILoginController {
    Mono<ServerResponse> login(ServerRequest request);
}
