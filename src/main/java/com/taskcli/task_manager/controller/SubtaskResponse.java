package com.taskcli.task_manager.controller;

public class SubtaskResponse {
    private Long id;
    private String title;
    private boolean completed;

    public SubtaskResponse(boolean completed, Long id, String title) {
        this.completed = completed;
        this.id = id;
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
