package org.skillbox.tasktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String name;

    private String description;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private TaskStatus status;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds = new HashSet<>();

    @ReadOnlyProperty
    private User author;

    @ReadOnlyProperty
    private User assignee;

    @ReadOnlyProperty
    private Set<User> observers = new HashSet<>();

    public void addObserver(User user){
        observerIds.add(user.getId());
        observers.add(user);
    }
}
