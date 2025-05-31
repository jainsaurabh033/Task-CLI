package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.SprintAnalyticsResponse;
import com.taskcli.task_manager.dto.SprintCreateRequest;
import com.taskcli.task_manager.model.Sprint;
import com.taskcli.task_manager.service.SprintService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sprints")
public class SprintController {

    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping
    public ResponseEntity<Sprint> createSprint(@Valid @RequestBody SprintCreateRequest request){
        Sprint sprint = sprintService.createSprint(request);
        return ResponseEntity.ok(sprint);
    }

    @GetMapping("/{sprintId}/analytics")
    public ResponseEntity<SprintAnalyticsResponse> getSprintAnalytics(@PathVariable Long sprintId) {
        return ResponseEntity.ok(sprintService.getSprintAnalytics(sprintId));
    }
}
