package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.response.TaskResponse;
import org.skillbox.tasktracker.dto.request.UpsertTaskRequest;
import org.skillbox.tasktracker.mapper.TaskMapper;
import org.skillbox.tasktracker.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    Flux<TaskResponse> getAllTask(){
        return taskService.findAll()
                .map(taskMapper::toTaskResponseFromTaskModel);
    }

    @GetMapping("/{id}")
    Mono<TaskResponse> getTaskById(@PathVariable String id){
        return taskService.findById(id)
                .map(taskMapper::toTaskResponseFromTaskModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<TaskResponse> createTask(@RequestBody UpsertTaskRequest taskRequest){
        return taskService.save(taskMapper.toTaskFromUpsertTaskRequest(taskRequest))
                .map(taskMapper::toTaskResponseFromTaskModel);
    }

    @PutMapping("/{id}/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<TaskResponse> addObservedUser(@PathVariable String id, @RequestBody String observedId){
        return taskService.addObserver(id,observedId).map(taskMapper::toTaskResponse);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<TaskResponse> updateTask(@PathVariable String id, @RequestBody UpsertTaskRequest taskRequest){
        return taskService.update(id,taskMapper.toTask(taskRequest)).map(taskMapper::toTaskResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteById(@PathVariable String id){
        return taskService.deleteById(id);
    }
}
