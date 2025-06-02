package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.RequestDTO.TaskCreateRequest;
import com.taskcli.task_manager.dto.ResponseDTO.TaskHistoryResponse;
import com.taskcli.task_manager.dto.ResponseDTO.UserProfileResponse;
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

    @PostMapping
    public ResponseEntity<Task> createTask(
            @Valid @RequestBody TaskCreateRequest request,
            @RequestParam Long creatorId
            ){
        Task task = taskService.createTask(request,creatorId);
        return ResponseEntity.ok(task);
    }


    @GetMapping
    public ResponseEntity<Page<Task>> getUserTasks(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(taskService.getTasksByUser(userId, page, size));
    }

    @GetMapping("/history")
    public ResponseEntity<List<TaskHistoryResponse>> getCompletedTasksWithFeedback(
            @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getCompletedTaskHistoryForUser(userId));
    }

    @PutMapping("/{taskId}/complete")
    public ResponseEntity<Task> markTaskCompleted(@PathVariable Long taskId){
        Task updatedTask = taskService.markTaskAsCompleted(taskId);
        return ResponseEntity.ok(updatedTask);
    }
}
