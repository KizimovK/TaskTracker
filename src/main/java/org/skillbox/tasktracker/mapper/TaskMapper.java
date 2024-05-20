package org.skillbox.tasktracker.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.skillbox.tasktracker.dto.TaskResponse;
import org.skillbox.tasktracker.dto.UpsertTaskRequest;
import org.skillbox.tasktracker.entity.Task;
import org.skillbox.tasktracker.mapper.delegate.TaskMapperDelegate;

@DecoratedWith(TaskMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskResponse toTaskResponse(Task task);

    Task toTask(UpsertTaskRequest taskRequest);
}
