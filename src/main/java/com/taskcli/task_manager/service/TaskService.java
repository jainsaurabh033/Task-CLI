package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.TaskCreateRequest;
import com.taskcli.task_manager.dto.TaskHistoryResponse;
import com.taskcli.task_manager.model.Feedback;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.FeedbackRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import com.taskcli.task_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final FeedbackRepository feedbackRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, FeedbackRepository feedbackRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Transactional
    public Task createTask(TaskCreateRequest request, Long creatorId){
        User creator = userRepository.findById(creatorId).orElseThrow(()-> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCreator(creator);
        task.setDueDate(request.getDueDate());

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
            Optional<Feedback> optionalFeedback = feedbackRepository.findByTaskAndIsPublicFalse(task);

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
}
