package com.taskcli.task_manager.dto.ResponseDTO;

import lombok.Data;

@Data
public class SuccessRegisterResponse {
    private String message;

    public SuccessRegisterResponse(String message) {
        this.message = message;
    }
}
