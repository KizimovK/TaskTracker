package org.skillbox.tasktracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.skillbox.tasktracker.dto.request.UpsertUserRequest;
import org.skillbox.tasktracker.dto.response.UserResponse;
import org.skillbox.tasktracker.entity.User;
import org.skillbox.tasktracker.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "name", target = "userName")
    User toUserFromUpsertUserRequest(UpsertUserRequest userRequest);
    UserModel toUserModelFromUser(User userModel);
    UserResponse toUserResponseFromUserModel(UserModel userModel);
}
