package org.skillbox.tasktracker.dto.response;

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
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private Set<RoleType> roles;
}
