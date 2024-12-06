package org.skillbox.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.skillbox.tasktracker.dto.request.UpsertUserRequest;
import org.skillbox.tasktracker.dto.response.UserResponse;
import org.skillbox.tasktracker.entity.User;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toUserFromUpsertUserRequest(UpsertUserRequest userRequest);

    UserResponse toUserResponse(User user);
}
