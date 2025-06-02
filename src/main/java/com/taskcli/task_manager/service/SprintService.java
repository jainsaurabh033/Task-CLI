package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.Role;
import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.ResponseDTO.SprintAnalyticsResponse;
import com.taskcli.task_manager.dto.RequestDTO.SprintCreateRequest;
import com.taskcli.task_manager.dto.ResponseDTO.SprintResponse;
import com.taskcli.task_manager.model.Sprint;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.FeedbackRepository;
import com.taskcli.task_manager.repository.SprintRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import com.taskcli.task_manager.repository.UserRepository;
import com.taskcli.task_manager.util.AuthUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public SprintService(SprintRepository sprintRepository,
                         TaskRepository taskRepository,
                         FeedbackRepository feedbackRepository,
                         UserRepository userRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public Sprint createSprint(SprintCreateRequest request){
        Optional<User> manager = userRepository.findById(request.getManagerId());

        if(manager.isEmpty()){
            throw new RuntimeException("Manager not found with ID");
        }

        if(!manager.get().getRole().equals(Role.MANAGER)){
            throw new RuntimeException("Only managers can create sprints");
        }

        Sprint sprint = new Sprint();
        sprint.setTitle(request.getName());
        sprint.setStartDate(request.getStartDate());
        sprint.setEndDate(request.getEndDate());

        return sprintRepository.save(sprint);
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

    public SprintResponse getCurrentSprintForCurrentUser(){
        String email = AuthUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        Sprint sprint = sprintRepository.findAll().stream()
                .filter(s-> !s.getStartDate().isAfter(today) && !s.getEndDate().isBefore(today))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("No active sprint found for today"));

        return new SprintResponse(
                sprint.getId(),
                sprint.getTitle(),
                sprint.getStartDate(),
                sprint.getEndDate()
        );
    }
}
