package com.webflux.demo.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.demo.controller.LoginController;
import com.webflux.demo.swagger.SwaggerLoginAPI;

@Configuration
public class LoginRoutes {
    
    @Bean
    @SwaggerLoginAPI
    RouterFunction<ServerResponse> loginRoute(LoginController loginController) {
        return RouterFunctions.route(RequestPredicates.POST("/auth/login"), loginController::login);
    }
}
