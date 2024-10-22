package org.skillbox.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.exception.EntityNotFoundException;
import org.skillbox.tasktracker.repository.UserRepository;
import org.skillbox.tasktracker.service.UserService;
import org.skillbox.tasktracker.utils.CopyUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(
                        MessageFormat.format("User not found by id {0}", id))));
    }

    @Override
    public Mono<User> save(User user) {
        log.info("Create new user: {}", user);
        return userRepository.insert(user);
    }

    @Override
    public Mono<User> update(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existedUser -> {
                    CopyUtil.nullNotCopy(user, existedUser);
                    log.info("Update user by id: {}", id);
                    return userRepository.save(existedUser);
                })
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(
                        MessageFormat.format("User not found by id {0}", id)
                )));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        log.info("User delete by id: {}", id);
        return userRepository.deleteById(id);
    }
}
