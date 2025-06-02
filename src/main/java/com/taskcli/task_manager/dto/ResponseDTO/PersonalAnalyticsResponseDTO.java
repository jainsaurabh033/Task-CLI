package com.taskcli.task_manager.dto.ResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class PersonalAnalyticsResponseDTO {
    private int completedTasks;
    private int feedbackCount;
    private List<RecentFeedbackDTO> recentFeedback;
}
