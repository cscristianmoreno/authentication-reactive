package com.webflux.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
public class CustomWebFilter implements WebFilter {
    
    @Autowired
    private ReactiveJwtDecoder reactiveJwtDecoder;
    
    private final String authorizationPrefix = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.equals("/auth/login") || path.equals("/users/save") || 
        path.equals("/webjars/swagger-ui/index.html")) {
            return chain.filter(exchange);
        }

        List<String> header = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            return webFilterResponse(exchange, "Authorization header is null!", HttpStatus.UNAUTHORIZED);
        }

        String authorization = header.get(0);

        if (!authorization.startsWith(authorizationPrefix)) {
            return webFilterResponse(exchange, "Authorization prefix is unknown!", HttpStatus.UNAUTHORIZED);
        }
        
        String token = authorization.split(" ")[1];

        return reactiveJwtDecoder
        .decode(token)
        .flatMap((claims) -> authenticate(claims))
        .flatMap((res) -> {
            return chain.filter(exchange)
            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(res));
        })
        .onErrorResume((error) -> {
            return webFilterResponse(exchange, error.getMessage(), HttpStatus.CONFLICT);
        });
    }
    
    private Mono<Authentication> authenticate(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList("scope");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
        new UsernamePasswordAuthenticationToken(
            jwt.getSubject(),
            null,
            AuthorityUtils.createAuthorityList(roles)
        );

        return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(usernamePasswordAuthenticationToken));        
    }

    private Mono<Void> webFilterResponse(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(HttpStatus.CONFLICT);
        DataBuffer buffer = DefaultDataBufferFactory.sharedInstance.allocateBuffer(256);
        buffer.write(message.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
