package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.FeedbackRequest;
import com.taskcli.task_manager.model.Subtask;
import com.taskcli.task_manager.service.SubtaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/subtasks")
public class SubtaskController {
    private final SubtaskService subtaskService;

    public SubtaskController(SubtaskService subtaskService){
        this.subtaskService = subtaskService;
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<Subtask> createSubtask(@PathVariable Long taskId, @RequestBody Subtask subtask) {
        Subtask createdSubtask = subtaskService.createSubtask(subtask, taskId);
        return ResponseEntity.ok(createdSubtask);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<FeedbackRequest.SubtaskResponse>> getSubtasksByTask(@PathVariable Long taskId) {
        List<FeedbackRequest.SubtaskResponse> responses  = subtaskService.getSubtasksByTaskId(taskId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{subtaskId}/status")
    public ResponseEntity<Subtask> updateStatus(@PathVariable Long subtaskId, @RequestParam boolean completed) {
        Subtask updated = subtaskService.updateSubtaskStatus(subtaskId, completed);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{subtaskId}")
    public ResponseEntity<Void> deleteSubtask(@PathVariable Long subtaskId) {
        subtaskService.deleteSubtask(subtaskId);
        return ResponseEntity.noContent().build();
    }

}
