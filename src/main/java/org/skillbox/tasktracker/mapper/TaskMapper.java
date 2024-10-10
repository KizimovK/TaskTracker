package org.skillbox.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.skillbox.tasktracker.dto.response.TaskResponse;
import org.skillbox.tasktracker.dto.request.UpsertTaskRequest;
import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.model.TaskModel;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskResponse toTaskResponseFromTaskModel(TaskModel taskModel);
    Task toTaskFromTaskModel(TaskModel taskModel);
    TaskModel toTaskModelFromTask(Task task);

    Task toTaskFromUpsertTaskRequest(UpsertTaskRequest taskRequest);
}
