package com.taskcli.task_manager.dto.RequestDTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateRequest {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    private Long sprintId;
}
