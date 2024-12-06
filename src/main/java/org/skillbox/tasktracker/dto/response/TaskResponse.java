package org.skillbox.tasktracker.dto.response;

import lombok.Data;
import org.skillbox.tasktracker.entity.TaskStatus;


import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
@Data
public class TaskResponse {

    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private UserResponse author;

    private UserResponse assignee;

    private Set<UserResponse> observers = new HashSet<>();
}
