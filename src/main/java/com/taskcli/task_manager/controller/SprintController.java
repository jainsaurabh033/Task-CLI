package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.SprintAnalyticsResponse;
import com.taskcli.task_manager.service.SprintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @GetMapping("/{sprintId}/analytics")
    public ResponseEntity<SprintAnalyticsResponse> getSprintAnalytics(@PathVariable Long sprintId) {
        return ResponseEntity.ok(sprintService.getSprintAnalytics(sprintId));
    }
}
