package com.taskcli.task_manager.dto.ResponseDTO;

import lombok.Data;

@Data
public class KanbanTaskResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String ownerName;
}
