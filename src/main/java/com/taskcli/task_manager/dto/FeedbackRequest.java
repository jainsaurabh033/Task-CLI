package com.taskcli.task_manager.dto;


public class FeedbackRequest {
    private Long managerId;
    private String message;
    private boolean publicFeedback;

    public boolean isPublicFeedback() {
        return publicFeedback;
    }

    public void setPublicFeedback(boolean publicFeedback) {
        this.publicFeedback = publicFeedback;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
