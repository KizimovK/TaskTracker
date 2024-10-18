package org.skillbox.tasktracker.service;

import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {

    Flux<Task> findAll();

    Mono<Task> findById(String idTask);

    Mono<Task> save(Task task);

    Mono<Task> update(String idTask, Task task);

    Mono<Task> addObserver(String idTask, String observerId);

    Mono<Void> deleteById(String idTask);
}
