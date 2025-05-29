package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.SprintAnalyticsResponse;
import com.taskcli.task_manager.dto.TaskCreateRequest;
import com.taskcli.task_manager.dto.TaskHistoryResponse;
import com.taskcli.task_manager.model.*;
import com.taskcli.task_manager.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;
    private final SprintRepository sprintRepository;
    private final SubtaskRepository subtaskRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, FeedbackRepository feedbackRepository,
                       SprintRepository sprintRepository,
                       SubtaskRepository subtaskRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
        this.sprintRepository = sprintRepository;
        this.subtaskRepository = subtaskRepository;
    }

    @Transactional
    public Task createTask(TaskCreateRequest request, Long creatorId){
        Optional<User> optionalUser = userRepository.findById(creatorId);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreator(optionalUser.get());
        task.setDueDate(request.getDueDate());

        if(request.getSprintId() != null){
            Optional<Sprint> optionalSprint = sprintRepository.findById(request.getSprintId());
            if(optionalSprint.isEmpty()) throw new RuntimeException("Sprint not found");
            task.setSprint(optionalSprint.get());
        }

        return taskRepository.save(task);
    }

    @Transactional
    public Page<Task> getTasksByUser(Long userId, int page, int size) {
        return taskRepository.findByCreatorIdWithUser(
                userId,
                PageRequest.of(page, size, Sort.by("dueDate").ascending())
        );
    }

    public List<TaskHistoryResponse> getCompletedTaskHistoryForUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        List<Task> completedTasks = taskRepository.findByCreatorIdAndStatus(userId, TaskStatus.COMPLETED);

        List<TaskHistoryResponse> responseList = new ArrayList<>();

        for(Task task : completedTasks){
            Optional<Feedback> optionalFeedback = feedbackRepository.findByTaskAndPublicFeedbackFalse(task);

            String privateFeedback = optionalFeedback.map(Feedback::getMessage).orElse("No private feedback");

            TaskHistoryResponse response = new TaskHistoryResponse();
            response.setTaskId(task.getId());
            response.setTitle(task.getTitle());
            response.setDescription(task.getDescription());
            response.setCompletedAt(task.getDueDate());
            response.setPrivateFeedback(privateFeedback);

            responseList.add(response);
        }

        return responseList;
    }

    public Task markTaskAsCompleted(Long taskId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new RuntimeException("Task not found with ID");
        }

        Task task = optionalTask.get();
        task.setStatus(TaskStatus.COMPLETED);

        for(Subtask subtask : task.getSubtasks()){
            subtask.setCompleted(true);
            subtaskRepository.save(subtask);
        }
        taskRepository.save(task);
        return task;
    }
}
