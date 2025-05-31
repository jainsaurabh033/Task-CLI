package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.FeedbackRequest;
import com.taskcli.task_manager.model.Feedback;
import com.taskcli.task_manager.service.FeedbackService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    @PostMapping("/task/{taskId}")
    public ResponseEntity<Feedback> addFeedback(
            @PathVariable Long taskId,
            @RequestBody FeedbackRequest feedbackRequest
    ){
        Feedback feedback = feedbackService.addFeedback(taskId, feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<Feedback>> getFeedbackForTask(@PathVariable Long taskId){
        return ResponseEntity.ok(feedbackService.getFeedbackForTask(taskId));
    }

    @GetMapping("/task/{taskId}/public")
    public ResponseEntity<List<Feedback>> getPublicFeedback(@PathVariable Long taskId){
        return ResponseEntity.ok(feedbackService.getPublicFeedbackForTask(taskId));
    }
}
