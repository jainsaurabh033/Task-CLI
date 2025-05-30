package com.taskcli.task_manager.dto;

import java.util.List;

public class PersonalAnalyticsResponseDTO {
    private int completedTasks;
    private int feedbackCount;
    private List<RecentFeedbackDTO> recentFeedback;

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }

    public List<RecentFeedbackDTO> getRecentFeedback() {
        return recentFeedback;
    }

    public void setRecentFeedback(List<RecentFeedbackDTO> recentFeedback) {
        this.recentFeedback = recentFeedback;
    }
}
