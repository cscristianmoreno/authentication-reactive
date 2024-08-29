package com.webflux.demo.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.demo.annotations.AuthorizeAdmin;
import com.webflux.demo.annotations.AuthorizeUser;
import com.webflux.demo.entity.Users;
import com.webflux.demo.interfaces.controller.IUserController;
import com.webflux.demo.services.UserRepositoryService;

import reactor.core.publisher.Mono;

@Component
public class UserController implements IUserController {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    public Mono<ServerResponse> save(ServerRequest request) {
        return request
        .bodyToMono(Users.class)
        .flatMap((body) -> {
            return userRepositoryService.save(body);
        })
        .flatMap((response) -> {
            return ServerResponse.ok().bodyValue(response);
        })
        .onErrorResume((error) -> {
            return ServerResponse.status(HttpStatus.CONFLICT).bodyValue(error.getMessage());
        })
        .switchIfEmpty(ServerResponse.status(HttpStatus.CONFLICT).build());
    }

    @Override
    public Mono<ServerResponse> update(ServerRequest request) {
        return request
        .bodyToMono(Users.class)
        .flatMap((user) -> {
            return userRepositoryService
            .findById(user.getId())
            .map((res) -> {
                return res;
            });
        })
        .flatMap((result) -> {
            return userRepositoryService.save(result);
        })
        .flatMap((result) -> {
            return ServerResponse.ok().bodyValue("Los cambios fueron almacenados con exito");
        })
        .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> findById(ServerRequest request) {
        final String requestId = request.pathVariable("id"); 
        final int id = Integer.parseInt(requestId);

        return userRepositoryService
        .findById(id)
        .flatMap((result) -> {
            return ServerResponse.ok().bodyValue(result); 
        })
        .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> findByUsername(ServerRequest request) {
        Optional<String> queryParam = request.queryParam("username");

        if (queryParam.isEmpty()) {
            return ServerResponse.badRequest().bodyValue("Query param 'username' is null!");
        }

        String username = queryParam.get();

        return userRepositoryService.findByUsername(username)
        .flatMap((response) -> {
            return ServerResponse.ok().bodyValue(response);
        })
        .switchIfEmpty(ServerResponse.notFound().build());
    }

    @Override
    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String pathVariable = request.pathVariable("id");
        int id = Integer.parseInt(pathVariable);
        
        return userRepositoryService.findById(id)
        .flatMap((res) -> {
            return userRepositoryService.deleteById(res.getId())
            .then(ServerResponse.ok().bodyValue("Usuario eliminado con éxito"));
        })
        .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue("El usuario no existe"));
    }

    @Override
    @AuthorizeAdmin
    public Mono<ServerResponse> accessOnlyAdmin(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Only user authorized Success!");
    }
    
    @Override
    @AuthorizeUser
    public Mono<ServerResponse> accessOnlyUser(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Only user authorized success");
    }
}
