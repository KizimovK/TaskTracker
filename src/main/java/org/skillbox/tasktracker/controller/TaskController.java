package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.response.TaskResponse;
import org.skillbox.tasktracker.dto.request.UpsertTaskRequest;
import org.skillbox.tasktracker.mapper.TaskMapper;
import org.skillbox.tasktracker.publisher.TaskPublisher;
import org.skillbox.tasktracker.security.AppUserDetails;
import org.skillbox.tasktracker.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskPublisher publisher;

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @GetMapping
    Flux<TaskResponse> getAllTask() {
        return taskService.findAll()
                .map(taskMapper::toTaskResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @GetMapping("/{taskId}")
    Mono<TaskResponse> getTaskById(@PathVariable String taskId) {
        return taskService.findById(taskId)
                .map(taskMapper::toTaskResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<TaskResponse> createTask(@RequestBody UpsertTaskRequest taskRequest,
                                  @AuthenticationPrincipal Mono<AppUserDetails> userDetailsMono) {
        return userDetailsMono.flatMap(userDetails -> {
                    taskRequest.setAuthorId(userDetails.getId());
                    return taskService.save(taskMapper.toTaskFromUpsertTaskRequest(taskRequest));
                })
                .map(taskMapper::toTaskResponse)
                .doOnSuccess(publisher::publisher);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @PutMapping("/{taskId}/addObserved")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<TaskResponse> addObservedUser(@PathVariable String taskId,
                                       @AuthenticationPrincipal Mono<AppUserDetails> userDetailsMono) {
        return userDetailsMono.flatMap(userDetails -> taskService.addObserver(taskId, userDetails.getId()))
                .map(taskMapper::toTaskResponse)
                .doOnSuccess(publisher::publisher);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<TaskResponse> updateTask(@PathVariable String id, @RequestBody UpsertTaskRequest taskRequest) {
        return taskService.update(id, taskMapper.toTask(taskRequest))
                .map(taskMapper::toTaskResponse)
                .doOnSuccess(publisher::publisher);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER','ROLE_USER')")
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<TaskResponse>> getStreamTask() {
        return publisher.getTaskResponseSink()
                .asFlux()
                .map(taskResponse -> ServerSentEvent.builder(taskResponse).build());
    }
}