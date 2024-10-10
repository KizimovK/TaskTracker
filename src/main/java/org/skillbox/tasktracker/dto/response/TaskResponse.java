package org.skillbox.tasktracker.dto.response;

import lombok.Data;
import org.skillbox.tasktracker.entity.TaskStatus;
import org.skillbox.tasktracker.entity.User;


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

    private User author;

    private User assignee;

    private Set<User> observers = new HashSet<>();
}
