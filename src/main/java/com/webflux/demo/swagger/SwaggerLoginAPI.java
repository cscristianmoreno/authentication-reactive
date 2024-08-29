package com.webflux.demo.swagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webflux.demo.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Retention(RetentionPolicy.RUNTIME)
@RouterOperations({
    @RouterOperation(
        path = "/auth/login",
        method = RequestMethod.POST,
        operation = @Operation(
            tags = "LOGIN",
            operationId = "/auth/login",
            description = "Autentica a un usuario",
            requestBody = @RequestBody(
                required = true,
                content = @Content(schema = @Schema(
                    implementation = LoginDTO.class
                ))
            ),
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                        schema = @Schema(defaultValue = "Bearer Token")
                    )
                ),
                @ApiResponse(
                    responseCode = "401",
                    content = @Content(
                        schema = @Schema(defaultValue = "Password not matches!")
                    )
                )
            }
        )
    )
})
public @interface SwaggerLoginAPI {
    
}
