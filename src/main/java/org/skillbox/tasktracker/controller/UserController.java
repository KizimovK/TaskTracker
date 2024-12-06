package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.request.UpsertUserRequest;
import org.skillbox.tasktracker.dto.response.UserResponse;
import org.skillbox.tasktracker.mapper.UserMapper;
import org.skillbox.tasktracker.security.AppUserDetails;
import org.skillbox.tasktracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @GetMapping
    public Flux<UserResponse> getAllUser(){
        return userService.findAll()
                .map(userMapper::toUserResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/currentUser")
    public Mono<UserResponse> getUserById(@AuthenticationPrincipal Mono<AppUserDetails> userDetailsMono){
        return userDetailsMono.flatMap(userDetails->userService.findById(userDetails.getId()))
                .map(userMapper::toUserResponse);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponse> createUser(@RequestBody UpsertUserRequest userRequest){
        return userService.save(userMapper.toUserFromUpsertUserRequest(userRequest))
                .map(userMapper::toUserResponse);
    }
    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<UserResponse> updateUser(@AuthenticationPrincipal Mono<AppUserDetails> userDetailsMono,
                                         @RequestBody UpsertUserRequest userRequest){
         return userDetailsMono.flatMap(userDetails ->
                 userService.update(userDetails.getId(), userMapper.toUserFromUpsertUserRequest(userRequest)))
                .map(userMapper::toUserResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public Mono<Void> deleteById(@AuthenticationPrincipal Mono<AppUserDetails> userDetailsMono){
        return userDetailsMono.flatMap(appUserDetails -> userService.deleteById(appUserDetails.getId()));
    }

}
