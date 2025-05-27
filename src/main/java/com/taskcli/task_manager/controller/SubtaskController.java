package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.model.Subtask;
import com.taskcli.task_manager.service.SubtaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/subtasks")
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
    public ResponseEntity<List<Subtask>> getSubtasksByTask(@PathVariable Long taskId) {
        List<Subtask> subtasks = subtaskService.getSubtasksByTaskId(taskId);
        return ResponseEntity.ok(subtasks);
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
