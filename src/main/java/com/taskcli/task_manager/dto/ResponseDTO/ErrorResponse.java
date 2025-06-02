package com.taskcli.task_manager.dto.ResponseDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timeStamp;
    private int status;
    private String message;
    private String details;

    public ErrorResponse(String details, String message, int status, LocalDateTime timeStamp) {
        this.details = details;
        this.message = message;
        this.status = status;
        this.timeStamp = timeStamp;
    }
}
