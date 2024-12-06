package org.skillbox.tasktracker.service;

import org.skillbox.tasktracker.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<User> findAll();

    Mono<User> findById(String id);

    Mono<User> save(User user);

    Mono<User> update(String id, User user);

    Mono<Void> deleteById(String id);

    Mono<User> findByUsername(String username);

}
