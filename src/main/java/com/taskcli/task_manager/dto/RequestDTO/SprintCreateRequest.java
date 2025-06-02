package com.taskcli.task_manager.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SprintCreateRequest {
    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private Long managerId;
}
