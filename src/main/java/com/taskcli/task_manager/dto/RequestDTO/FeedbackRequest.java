package com.taskcli.task_manager.dto.RequestDTO;


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

    public static class SubtaskResponse {
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
}