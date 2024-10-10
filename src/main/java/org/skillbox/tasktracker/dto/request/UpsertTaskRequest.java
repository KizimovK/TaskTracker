package org.skillbox.tasktracker.dto.request;

import lombok.Data;

@Data
public class UpsertTaskRequest {

    private String name;

    private String description;

    private String authorId;

    private String assigneeId;

}
