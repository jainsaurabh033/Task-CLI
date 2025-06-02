package com.taskcli.task_manager.dto.ResponseDTO;

import lombok.Data;

@Data
public class RegisterResponse {
    private String message;

    public RegisterResponse(String message) {
        this.message = message;
    }
}
