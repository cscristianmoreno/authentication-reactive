package com.webflux.demo.swagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webflux.demo.dto.LoginDTO;
import com.webflux.demo.entity.Users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Retention(RetentionPolicy.RUNTIME)
@RouterOperations({
    @RouterOperation(
        path = "/users/save", 
        method = RequestMethod.POST, 
        operation = @Operation(
            tags = "USUARIOS",
            description = "Almacena un usuario",
            operationId = "userSave",
            requestBody = @RequestBody(
                content = @Content(
                    schema = @Schema(implementation = Users.class)
                )
            ),
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(implementation = Users.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                        schema = @Schema(implementation = BadCredentialsException.class)
                    )
                )
            }
        )
    ),

    @RouterOperation(
        path = "/users/update/",
        method = RequestMethod.PUT,
        operation = @Operation(
            tags = "USUARIOS",
            description = "Actualiza un usuario",
            operationId = "userUpdate",
            requestBody = @RequestBody(
                content = @Content(
                    schema = @Schema(implementation = Users.class)
                )
            ),
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(implementation = Users.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                        schema = @Schema(implementation = BadCredentialsException.class)
                    )
                )
            }
        )
    ),

    @RouterOperation(
        path = "/users/{id}/",
        method = RequestMethod.GET,
        operation = @Operation(
            tags = "USUARIOS",
            description = "Obtiene un usuario",
            operationId = "userGet",
            requestBody = @RequestBody(
                content = @Content(
                    schema = @Schema(implementation = Users.class)
                )
            ),
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(implementation = Users.class)
                    )
                ),
                @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                        schema = @Schema(implementation = BadCredentialsException.class)
                    )
                )
            }
        )
    ),
    
    @RouterOperation(
        path = "/users/{id}/",
        method = RequestMethod.DELETE,
        operation = @Operation(
            tags = "USUARIOS",
            description = "Elimina un usuario",
            operationId = "userDelete",
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(defaultValue = "User delete succefully")
                    )
                ),
                @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                        schema = @Schema(implementation = BadCredentialsException.class)
                    )
                )
            }
        )
    ),

    @RouterOperation(
        path = "/auth/login",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE },
        operation = @Operation(
            description = "Autentica a un usuario",
            requestBody = @RequestBody(
                required = true,
                content = @Content(schema = @Schema(
                    implementation = LoginDTO.class
                ))
            )
        )
    )
})
public @interface SwaggerUsersAPI {
    
}
