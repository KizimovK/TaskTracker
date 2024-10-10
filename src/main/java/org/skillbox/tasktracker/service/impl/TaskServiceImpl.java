package org.skillbox.tasktracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.exception.EntityNotFoundException;
import org.skillbox.tasktracker.mapper.TaskMapper;
import org.skillbox.tasktracker.model.TaskModel;
import org.skillbox.tasktracker.repository.TaskRepository;
import org.skillbox.tasktracker.repository.UserRepository;
import org.skillbox.tasktracker.service.TaskService;
import org.springframework.beans.BeanUtils;
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
    private final TaskMapper taskMapper;


    @Override
    public Flux<TaskModel> findAll() {
        return taskRepository.findAll()
                .map(taskMapper::toTaskModelFromTask);
    }

    @Override
    public Mono<TaskModel> findById(String id) {
        return taskRepository.findById(id)
                .map(taskMapper::toTaskModelFromTask)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException(
                        MessageFormat.format("Task not found by id {0}", id))));
    }

//ToDO: refracting method save change User on UserModel, setStatus do not make
    @Override
    public Mono<TaskModel> save(Task task) {
        Mono<TaskModel> taskModelMono = Mono.zip(userRepository.existsById(task.getAuthorId()), userRepository.existsById(task.getAssigneeId()))
                .flatMap(tuple -> {
                    if (tuple.getT1() && tuple.getT2()) {
                        return taskRepository.save(task);
                    } else {
                        return Mono.error(() -> new EntityNotFoundException("The author or assignee was not found among the users"));
                    }
                })
                .map(taskMapper::toTaskModelFromTask);
        log.info("Create new task: {}", task.getName());
        return Mono.zip(taskModelMono, userRepository.findById(task.getAuthorId()), userRepository.findById(task.getAssigneeId()))
                .map(t -> {
                    t.getT1().setAuthor(t.getT2());
                    t.getT1().setAssignee(t.getT3());
                    return  t.getT1();
                });
    }

    private Mono<TaskModel> setFieldsTaskModel(Mono<TaskModel> taskModelMono,Mono<User> author,
                                               Mono<User> assignee, Flux<User> observedFlux){
       Mono<TaskModel> taskModel =  Mono.zip(taskModelMono, author, assignee)
                .map(t -> {
                    t.getT1().setAuthor(t.getT2());
                    t.getT1().setAssignee(t.getT3());
                    return  t.getT1();
                });
       if (observedFlux.hasElements().block()){
           taskModel.zipWith(observedFlux, (t, o) -> {
               t.addObserver();
           })
       }
       return taskModel;

    }
    // TOdo: update and addObserver
    @Override
    public Mono<TaskModel> update(String id, Task task) {
        return taskRepository.findById(id).flatMap(existedTask -> {
                    BeanUtils.copyProperties(task, existedTask);
                    return taskRepository.save(existedTask);
                })
                .map(taskMapper::toTaskModelFromTask);
    }

    @Override
    public Mono<TaskModel> addObserver(String idTask, String observerId) {
        return taskRepository.findById(idTask).flatMap(existedTask -> {
//            User observed = userService.findById(observerId).block();
//            existedTask.addObserver(observed);
                    return taskRepository.save(existedTask);
                })
                .map(taskMapper::toTaskModelFromTask);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

}
