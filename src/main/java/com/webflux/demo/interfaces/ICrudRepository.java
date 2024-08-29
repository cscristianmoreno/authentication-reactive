package com.webflux.demo.interfaces;

import reactor.core.publisher.Mono;

public interface ICrudRepository<T> {
    Mono<T> save(T entity);

    Mono<T> update(T entity);

    Mono<T> findById(int id);

    Mono<Void> deleteById(int id);
}
