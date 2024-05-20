package org.skillbox.tasktracker.mapper.delegate;

import org.skillbox.tasktracker.dto.TaskResponse;
import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.mapper.TaskMapper;
import org.skillbox.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public abstract class TaskMapperDelegate implements TaskMapper {
    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskResponse toTaskResponse(Task task) {
        if (task == null) {
            return null;
        } else {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setId(task.getId());
            taskResponse.setName(task.getName());
            taskResponse.setDescription(taskResponse.getDescription());
            taskResponse.setCreatedAt(taskResponse.getCreatedAt());
            taskResponse.setUpdatedAt(task.getUpdatedAt());
            taskResponse.setAuthor(userRepository.findById(task.getAuthorId()).block());
            taskResponse.setAssignee(userRepository.findById(task.getAssigneeId()).block());
            taskResponse.setObservers(task.getObserverIds().stream()
                    .map(id -> userRepository.findById(id).block()).collect(Collectors.toSet()));
            return taskResponse;
        }
    }

}
