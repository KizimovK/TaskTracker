package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.request.UpsertUserRequest;
import org.skillbox.tasktracker.dto.response.UserResponse;
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
        return userService.findAll()
                .map(userMapper::toUserResponseFromUserModel);
    }

    @GetMapping("/{id}")
    public Mono<UserResponse> getUserById(@PathVariable String id){
        return userService.findById(id)
                .map(userMapper::toUserResponseFromUserModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUser(@RequestBody UpsertUserRequest userRequest){
        return userService.save(userMapper.toUserFromUpsertUserRequest(userRequest))
                .map(userMapper::toUserResponseFromUserModel);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<UserResponse> updateUser(@PathVariable String id, @RequestBody UpsertUserRequest userRequest){
         return userService.update(id, userMapper.toUserFromUpsertUserRequest(userRequest))
                .map(userMapper::toUserResponseFromUserModel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteById(@PathVariable String id){
        return userService.deleteById(id);
    }

}
