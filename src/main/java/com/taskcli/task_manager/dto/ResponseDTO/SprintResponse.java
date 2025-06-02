package com.taskcli.task_manager.dto.ResponseDTO;
import lombok.Data;
import java.time.LocalDate;


@Data
public class SprintResponse {
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    public SprintResponse(Long id, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
