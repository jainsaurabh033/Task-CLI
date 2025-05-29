package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.SprintAnalyticsResponse;
import com.taskcli.task_manager.model.Sprint;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.repository.FeedbackRepository;
import com.taskcli.task_manager.repository.SprintRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final FeedbackRepository feedbackRepository;

    public SprintService(SprintRepository sprintRepository,
                         TaskRepository taskRepository,
                         FeedbackRepository feedbackRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public SprintAnalyticsResponse getSprintAnalytics(Long sprintId) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        if (optionalSprint.isEmpty()) {
            throw new RuntimeException("Sprint not found with ID: " + sprintId);
        }

        Sprint sprint = optionalSprint.get();
        List<Task> tasks = taskRepository.findBySprint(sprint);

        int totalTasks = tasks.size();
        int completedTasks = 0;
        int feedbackCount = 0;

        for (Task task : tasks) {
            if (task.getStatus() == TaskStatus.COMPLETED) {
                completedTasks++;
            }
            feedbackCount += feedbackRepository.countByTask(task);
        }

        SprintAnalyticsResponse response = new SprintAnalyticsResponse();
        response.setSprintId(sprint.getId());
        response.setSprintName(sprint.getTitle());
        response.setTotalTasks(totalTasks);
        response.setCompletedTasks(completedTasks);
        response.setTotalFeedbacks(feedbackCount);

        double rate = totalTasks > 0 ? (completedTasks * 100.0) / totalTasks : 0.0;
        response.setAverageRating(rate);

        return response;
    }
}
