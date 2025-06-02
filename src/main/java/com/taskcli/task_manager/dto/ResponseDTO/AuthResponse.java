package com.taskcli.task_manager.dto.ResponseDTO;

import com.taskcli.task_manager.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private Long id;
    private String name;
    private String email;
    private Role role;

    public AuthResponse(String email, Long id, String name, Role role, String token) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.role = role;
        this.token = token;
    }
}
