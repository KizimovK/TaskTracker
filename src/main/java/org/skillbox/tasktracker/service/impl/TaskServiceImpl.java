package org.skillbox.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.entity.TaskStatus;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.exception.EntityNotFoundException;
import org.skillbox.tasktracker.repository.TaskRepository;
import org.skillbox.tasktracker.repository.UserRepository;
import org.skillbox.tasktracker.service.TaskService;
import org.skillbox.tasktracker.utils.CopyUtil;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    private Mono<Task> getFullTask(Task task) {
        Mono<User> authorMono = userRepository.findById(task.getAuthorId());
        Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId());
        Flux<User> observerFlux = userRepository.findAllById(task.getObserverIds());
        Mono<Task> taskMonoAllFields = Mono.zip(authorMono, assigneeMono).map(tuple -> {
            task.setAuthor(tuple.getT1());
            task.setAssignee(tuple.getT2());
            return task;
        });
        return taskMonoAllFields.zipWith(observerFlux.collectList(), (taskFull, observedList) -> {
            observedList.forEach(taskFull::addObserver);
            return taskFull;
        });
    }

    @Override
    public Flux<Task> findAll() {
        log.info("Find all task");
        return taskRepository.findAll().flatMap(this::getFullTask);
    }

    @Override
    public Mono<Task> findById(String idTask) {

        return taskRepository.findById(idTask)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(
                        MessageFormat.format("Task not found by id {0}", idTask))))
                .flatMap(this::getFullTask);
    }


    @Override
    public Mono<Task> save(Task task) {
        log.info("Save new task");
        return Mono.zip(userRepository.existsById(task.getAuthorId()), userRepository.existsById(task.getAssigneeId()))
                .flatMap(tuple -> {
                    if (!tuple.getT1()) {
                        return Mono.error(() -> new EntityNotFoundException("The author  was not found among the users"));
                    }
                    if (!tuple.getT2()) {
                        return Mono.error(() -> new EntityNotFoundException("The assignee was not found among the users"));
                    }
                    task.setStatus(TaskStatus.TODO);

                    return taskRepository.insert(task);
                })
                .flatMap(this::getFullTask);

    }

    @Override
    public Mono<Task> update(String idTask, Task task) {
        log.info("Update task id: {}", idTask);
        return findById(idTask).flatMap(existedTask -> {
                    CopyUtil.nullNotCopy(task, existedTask);
                    return taskRepository.save(existedTask);
                })
                .flatMap(this::getFullTask);
    }

    @Override
    public Mono<Task> addObserver(String idTask, String observerId) {

        Mono<Task> taskMono = taskRepository.findById(idTask)
                .switchIfEmpty(Mono.error(() ->
                        new EntityNotFoundException(
                                MessageFormat.format("Task not found by this id {0}", idTask))));

        Mono<User> observerMono = userRepository.findById(observerId)
                .switchIfEmpty(Mono.error(() ->
                        new EntityNotFoundException(
                                MessageFormat.format("User (observed) not found by this id {0}", observerId))));

        log.info("Add observed user id: {} in task id: {}", observerId, idTask);

        return Mono.zip(taskMono, observerMono)
                .flatMap(tuple -> {
                    tuple.getT1().addObserver(tuple.getT2());
                    return taskRepository.save(tuple.getT1());
                })
                .flatMap(this::getFullTask);
    }


    @Override
    public Mono<Void> deleteById(String idTask) {
        log.info("delete task id: {}", idTask);
        return taskRepository.deleteById(idTask);
    }
}
