package com.taskcli.task_manager.dto.ResponseDTO;
import lombok.Data;

@Data
public class RecentFeedbackDTO {
    private String taskTitle;
    private int rating;
    private String feedbackMessage;
    private String type;
}
