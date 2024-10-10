package org.skillbox.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.skillbox.tasktracker.entity.TaskStatus;
import org.skillbox.tasktracker.entity.User;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskModel {
    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds = new HashSet<>();

    private UserModel author;

    private UserModel assignee;

    private Set<UserModel> observers = new HashSet<>();
    public void addObserver(UserModel user){
        observerIds.add(user.getId());
        observers.add(user);
    }
}
