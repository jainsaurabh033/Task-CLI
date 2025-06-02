package com.taskcli.task_manager.dto.ResponseDTO;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskHistoryResponse {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate completedAt;
    private String privateFeedback;
}
