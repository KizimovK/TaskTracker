package org.skillbox.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.skillbox.tasktracker.dto.UpsertUserRequest;
import org.skillbox.tasktracker.dto.UserResponse;
import org.skillbox.tasktracker.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "name", target = "userName")
    User toUser(UpsertUserRequest userRequest);

    UserResponse toUserResponse(User user);
}
