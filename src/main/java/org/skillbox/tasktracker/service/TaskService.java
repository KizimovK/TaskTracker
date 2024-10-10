package org.skillbox.tasktracker.service;

import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.model.TaskModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Flux<TaskModel> findAll();

    Mono<TaskModel> findById(String id);

//    Mono<Task> save(Task task, User user);

    Mono<TaskModel> save(Task task);

    Mono<TaskModel> update(String idTask, Task task);

    Mono<TaskModel> addObserver(String idTask, String observerId);

    Mono<Void> deleteById(String id);
}
