package org.skillbox.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillbox.tasktracker.dto.TaskResponse;
import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.mapper.TaskMapper;
import org.skillbox.tasktracker.repository.TaskRepository;
import org.skillbox.tasktracker.service.TaskService;
import org.skillbox.tasktracker.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Override
    public Flux<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Mono<Task> findById(String id) {
        return taskRepository.findById(id);
    }

//    @Override
//    public Mono<Task> save(Task task, User user) {
//        return null;
//    }

    @Override
    public Mono<Task> save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Mono<Task> update(String id, Task task) {
        return taskRepository.findById(id).flatMap(existedTask -> {
            BeanUtils.copyProperties(task,existedTask);
            return taskRepository.save(existedTask);
        });
    }

    @Override
    public Mono<Task> addObserver(String id, String observerId) {
        return taskRepository.findById(id).flatMap(existedTask ->{
            User observed = userService.findById(observerId).block();
            existedTask.addObserver(observed);
            return taskRepository.save(existedTask);
        });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }
}
