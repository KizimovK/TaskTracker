package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.UpsertUserRequest;
import org.skillbox.tasktracker.dto.UserResponse;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.mapper.UserMapper;
import org.skillbox.tasktracker.service.UserService;
import org.springframework.http.HttpStatus;
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
    public Flux<UserResponse> getAllUser(){
        return userService.findAll().map(userMapper::toUserResponse);
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id){
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUser(@RequestBody UpsertUserRequest userRequest){
        return userService.save(userMapper.toUser(userRequest)).map(userMapper::toUserResponse);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<UserResponse> updateUser(@PathVariable String id, @RequestBody UpsertUserRequest userRequest){
         return userService.update(id, userMapper.toUser(userRequest))
                .map(userMapper::toUserResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteById(@PathVariable String id){
        return userService.deleteById(id);
    }

}
