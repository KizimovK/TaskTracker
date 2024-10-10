package org.skillbox.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.exception.EntityNotFoundException;
import org.skillbox.tasktracker.mapper.UserMapper;
import org.skillbox.tasktracker.model.UserModel;
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
    private final UserMapper userMapper;

    @Override
    public Flux<UserModel> findAll() {
        return userRepository.findAll().map(userMapper::toUserModelFromUser);
    }

    @Override
    public Mono<UserModel> findById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toUserModelFromUser)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(
                        MessageFormat.format("User not found by id {0}", id))));
    }

    @Override
    public Mono<UserModel> save(User user) {
        log.info("Create new user: {}", user);
        return userRepository.insert(user)
                .map(userMapper::toUserModelFromUser);
    }

    @Override
    public Mono<UserModel> update(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existedUser -> {
                    CopyUtil.nullNotCopy(user, existedUser);
                    log.info("Update user by id: {}", id);
                    return userRepository.save(existedUser);
                })
                .map(userMapper::toUserModelFromUser)
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
