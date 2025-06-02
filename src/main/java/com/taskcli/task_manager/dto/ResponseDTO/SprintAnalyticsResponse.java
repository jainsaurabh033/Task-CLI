package com.taskcli.task_manager.dto.ResponseDTO;

import lombok.Data;

@Data
public class SprintAnalyticsResponse {
    private Long sprintId;
    private String sprintName;
    private int totalTasks;
    private int completedTasks;
    private double averageRating;
    private int totalFeedbacks;
}
