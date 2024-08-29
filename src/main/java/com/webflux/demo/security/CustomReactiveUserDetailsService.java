package com.webflux.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webflux.demo.roles.Roles;
import com.webflux.demo.services.UserRepositoryService;

import reactor.core.publisher.Mono;

@Service
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepositoryService.findByUsername(username)
        .map((user) -> {
            List<GrantedAuthority> authorityUtils = AuthorityUtils.createAuthorityList(
                user.getRole().stream().map(Roles::name).toList()
            );

            return User
            .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorityUtils)
            .build();
        })
        .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found!")));
    }
    
}
