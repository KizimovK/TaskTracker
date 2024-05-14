package org.skillbox.tasktracker.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
}
