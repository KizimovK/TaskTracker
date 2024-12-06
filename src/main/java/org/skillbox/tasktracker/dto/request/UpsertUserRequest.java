package org.skillbox.tasktracker.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.skillbox.tasktracker.entity.RoleType;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class UpsertUserRequest {
    private String username;
    private String email;
    private Set<RoleType> roles;
    private String password;
}
