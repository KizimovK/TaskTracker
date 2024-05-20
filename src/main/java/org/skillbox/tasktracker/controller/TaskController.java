package org.skillbox.tasktracker.controller;

import lombok.RequiredArgsConstructor;
import org.skillbox.tasktracker.dto.TaskResponse;
import org.skillbox.tasktracker.dto.UpsertTaskRequest;
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
        return taskService.findAll().map(taskMapper::toTaskResponse);
    }

    @GetMapping("/{id}")
    Mono<TaskResponse> getTaskById(@PathVariable String id){
        return taskService.findById(id).map(taskMapper::toTaskResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<TaskResponse> createTask(@RequestBody UpsertTaskRequest taskRequest){
        return taskService.save(taskMapper.toTask(taskRequest)).map(taskMapper::toTaskResponse);
    }



}
