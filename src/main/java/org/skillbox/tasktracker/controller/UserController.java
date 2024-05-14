package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.UpsertUserRequest;
import org.skillbox.tasktracker.dto.UserResponse;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.mapper.UserMapper;
import org.skillbox.tasktracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public Flux<User> getAllUser(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUserById(@PathVariable String id){
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUser(@RequestBody UpsertUserRequest userRequest){
        return userService.save(userMapper.toUser(userRequest))
                .map(userMapper::toUserResponse)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> updateUser(@PathVariable String id, @RequestBody UpsertUserRequest userRequest){
        return userService.update(id, userMapper.toUser(userRequest))
                .map(userMapper::toUserResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
        return userService.deleteById(id)
                .map(ResponseEntity::ok);
    }

}
