package com.webflux.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webflux.demo.entity.Authority;
import com.webflux.demo.interfaces.services.IAuthorityRepositoryService;
import com.webflux.demo.repository.AuthorityRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthorityRepositoryService implements IAuthorityRepositoryService {
    
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Mono<Authority> save(Authority entity) {
        return authorityRepository.save(entity);
    }

    @Override
    public Mono<Authority> update(Authority entity) {
        return authorityRepository.save(entity);
    }

    @Override
    public Mono<Authority> findById(int id) {
        return authorityRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(int id) {
        return authorityRepository.deleteById(id);
    }

    @Override
    public Mono<Authority> findByRole(String role) {
        return authorityRepository.findByRole(role);
    }


}
