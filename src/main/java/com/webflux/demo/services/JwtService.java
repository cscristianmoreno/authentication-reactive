package com.webflux.demo.services;

import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.webflux.demo.interfaces.services.IJwtService;

import reactor.core.publisher.Mono;

@Service
public class JwtService implements IJwtService {
    
    @Value("${jwt.secret.issuer}")
    private String issuer;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Override
    public Mono<String> encode(Authentication authentication) {

        String subject = authentication.getName();
        List<String> scope = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority
        ).toList();

        Instant now = Instant.now();
        long expire = 3600L;

        JwtClaimsSet claims = JwtClaimsSet
        .builder()
            .issuer(issuer)
            .subject(subject)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expire))
            .claim("scope", scope)
        .build();

        return Mono.just(
            jwtEncoder.encode(JwtEncoderParameters.from(claims))
            .getTokenValue()
        );
    }
}
