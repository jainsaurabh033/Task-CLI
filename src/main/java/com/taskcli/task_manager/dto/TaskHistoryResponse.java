package com.taskcli.task_manager.dto;

import java.time.LocalDate;

public class TaskHistoryResponse {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate completedAt;
    private String privateFeedback;

    public LocalDate getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDate completedAt) {
        this.completedAt = completedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrivateFeedback() {
        return privateFeedback;
    }

    public void setPrivateFeedback(String privateFeedback) {
        this.privateFeedback = privateFeedback;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
