package com.webflux.demo.routes;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.webflux.demo.controller.UserController;
import com.webflux.demo.swagger.SwaggerUsersAPI;

@Configuration
public class UserRoutes {
    
    @Bean
    @SwaggerUsersAPI
    RouterFunction<ServerResponse> userRoute(final UserController userController) {
        return RouterFunctions
        .nest(path("/users"),
            route(POST("/save"), userController::save)
            .andRoute(PUT("/update"), userController::update)
            .andRoute(GET("/{id}/"), userController::findById)
            .andRoute(GET("/username"), userController::findByUsername)
            .andRoute(GET("/authorize/admin"), userController::accessOnlyAdmin)
            .andRoute(GET("/authorize/user"), userController::accessOnlyUser)
        );
    }
}
