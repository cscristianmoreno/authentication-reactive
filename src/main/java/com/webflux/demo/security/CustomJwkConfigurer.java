package com.webflux.demo.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class CustomJwkConfigurer {
    
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;

    @Bean
    JwtEncoder jwtEncoder() {
        RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<SecurityContext>(jwkSet);
        NimbusJwtEncoder encoder = new NimbusJwtEncoder(jwkSource);
        return encoder;
    }

    @Bean
    ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(publicKey).build();
    }
}
