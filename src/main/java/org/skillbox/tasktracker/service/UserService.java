package org.skillbox.tasktracker.service;

import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.model.UserModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserModel> findAll();

    Mono<UserModel> findById(String id);

    Mono<UserModel> save(User user);

    Mono<UserModel> update(String id, User user);

    Mono<Void> deleteById(String id);
}
