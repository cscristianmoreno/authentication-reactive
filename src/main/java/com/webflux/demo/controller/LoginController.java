package com.webflux.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.demo.dto.LoginDTO;
import com.webflux.demo.interfaces.controller.ILoginController;
import com.webflux.demo.services.AuthenticationService;

import reactor.core.publisher.Mono;

@Component
public class LoginController implements ILoginController {

    @Autowired
    private AuthenticationService authenticationService;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginDTO.class)
        .flatMap((login) -> {
            return authenticationService.authenticate(login);
        })
        .flatMap((token) -> ServerResponse.ok().bodyValue(token))
        .onErrorResume((res) -> {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED)
            .bodyValue(res.getMessage());
        });
    }
}
