package org.skillbox.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.skillbox.tasktracker.dto.response.TaskResponse;
import org.skillbox.tasktracker.dto.request.UpsertTaskRequest;
import org.skillbox.tasktracker.entity.Task;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {




    Task toTaskFromUpsertTaskRequest(UpsertTaskRequest taskRequest);

    Task toTask(UpsertTaskRequest taskRequest);

    TaskResponse toTaskResponse(Task task);
}