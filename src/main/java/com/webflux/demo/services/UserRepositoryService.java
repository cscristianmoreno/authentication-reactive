package com.webflux.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webflux.demo.entity.Users;
import com.webflux.demo.interfaces.services.IUserRepositoryService;
import com.webflux.demo.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserRepositoryService implements IUserRepositoryService {

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Users> save(Users entity) {
        String password = entity.getPassword();
        entity.setPassword(passwordEncoder.encode(password));

        return userRepository.save(entity);
    }

    @Override
    public Mono<Users> update(Users entity) {
        return userRepository.save(entity);
    }

    @Override
    public Mono<Users> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<Void> deleteById(int id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
}
