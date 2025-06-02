package com.taskcli.task_manager.dto.ResponseDTO;

import com.taskcli.task_manager.Enum.Role;
import lombok.Data;

@Data
public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;

    public UserProfileResponse( Long id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
