package org.skillbox.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.repository.UserRepository;
import org.skillbox.tasktracker.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
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
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> save(User user) {
//        user.setId(UUID.randomUUID().toString());
        log.info("Create new user: {}", user);
        return userRepository.insert(user);
    }

    @Override
    public Mono<User> update(String id, User user) {
        log.info("Update user by id: {}", id);
        return userRepository.findById(id).flatMap(updateUser->{
            // todo: из одного в другой объект, объекты одинакового типа, поля содержащие null не переносятся
            // при обновлении записи MongoDB создает новый ID
            return userRepository.save(updateUser);
        });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        log.info("User delete by id: {}", id);
        return userRepository.deleteById(id);
    }
}
