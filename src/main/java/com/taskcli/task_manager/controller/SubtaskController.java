package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.model.Subtask;
import com.taskcli.task_manager.service.SubtaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/subtasks")
public class SubtaskController {
    private final SubtaskService subtaskService;

    public SubtaskController(SubtaskService subtaskService){
        this.subtaskService = subtaskService;
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Subtask> markComplete(@PathVariable Long id){
        Subtask updated = subtaskService.markSubtaskAsComplete(id);
        return ResponseEntity.ok(updated);
    }
}
