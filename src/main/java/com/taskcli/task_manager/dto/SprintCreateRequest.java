package com.taskcli.task_manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SprintCreateRequest {
    @NotBlank
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    private Long managerId;

    public @NotNull LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDate startDate) {
        this.startDate = startDate;
    }
}
