package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.PersonalAnalyticsResponseDTO;
import com.taskcli.task_manager.dto.RecentFeedbackDTO;
import com.taskcli.task_manager.model.Feedback;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.repository.FeedbackRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonalAnalyticsService {

    private final TaskRepository taskRepository;
    private final FeedbackRepository feedbackRepository;

    public PersonalAnalyticsService(FeedbackRepository feedbackRepository, TaskRepository taskRepository) {
        this.feedbackRepository = feedbackRepository;
        this.taskRepository = taskRepository;
    }

    public PersonalAnalyticsResponseDTO getUserAnalytics(Long userId) {
        // 1. Fetch completed tasks for the user
        List<Task> completedTasks = taskRepository.findByCreatorIdAndStatus(userId, TaskStatus.COMPLETED);

        // 2. Fetch feedbacks given to the user
        List<Feedback> feedbacks = feedbackRepository.findByReceiverId(userId);

        // 3. Prepare recent feedback DTOs
        List<RecentFeedbackDTO> recentFeedback = feedbacks.stream()
                .map(fb -> {
                    RecentFeedbackDTO dto = new RecentFeedbackDTO();
                    dto.setTaskTitle(fb.getTask().getTitle());
                    dto.setFeedbackMessage(fb.getMessage());
                    dto.setType(fb.isPublicFeedback() ? "PUBLIC" : "PRIVATE");
                    return dto;
                })
                .collect(Collectors.toList());

        // 4. Build response
        PersonalAnalyticsResponseDTO response = new PersonalAnalyticsResponseDTO();
        response.setCompletedTasks(completedTasks.size());
        response.setFeedbackCount(feedbacks.size());
        response.setRecentFeedback(recentFeedback);

        return response;
    }
}
