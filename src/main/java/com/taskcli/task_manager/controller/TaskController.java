package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.RequestDTO.TaskCreateRequest;
import com.taskcli.task_manager.dto.ResponseDTO.TaskHistoryResponse;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    private final TaskService taskService;


    @Operation(summary = "Create a new task")
    @PostMapping
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody TaskCreateRequest request,
            @RequestParam Long creatorId
            ){
        Task task = taskService.createTask(request,creatorId);
        return ResponseEntity.ok(task);
    }


    @Operation(summary = "Get tasks for a user")
    @GetMapping
    public ResponseEntity<Page<Task>> getUserTasks(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId, page, size));
    }

    @Operation(summary = "Get completed task history with feedback")
    @GetMapping("/history")
    public ResponseEntity<List<TaskHistoryResponse>> getCompletedTasksWithFeedback(
            @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getCompletedTaskHistoryForUser(userId));
    }

    @Operation(summary = "Mark task as completed")
    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskCompleted(@PathVariable Long taskId){
        Task updatedTask = taskService.markTaskAsCompleted(taskId);
        return ResponseEntity.ok(updatedTask);
    }
}
