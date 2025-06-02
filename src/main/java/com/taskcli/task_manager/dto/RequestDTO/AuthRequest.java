package com.taskcli.task_manager.dto.RequestDTO;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
