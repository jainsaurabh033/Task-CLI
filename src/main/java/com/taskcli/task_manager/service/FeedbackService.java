package com.taskcli.task_manager.service;

import com.taskcli.task_manager.dto.FeedbackRequest;
import com.taskcli.task_manager.model.Feedback;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.FeedbackRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import com.taskcli.task_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           TaskRepository taskRepository,
                           UserRepository userRepository){
        this.feedbackRepository = feedbackRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Feedback addFeedback(Long taskId, FeedbackRequest request){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Optional<User> optionalManager = userRepository.findById(request.getManagerId());

        if(optionalTask.isEmpty() || optionalManager.isEmpty()){
            throw new RuntimeException("Task or Manager not found");
        }

        Feedback feedback = new Feedback();
        feedback.setTask(optionalTask.get());
        feedback.setManager(optionalManager.get());
        feedback.setMessage(request.getMessage());
        feedback.setPublic(request.isPublic());

        feedbackRepository.save(feedback);
        return feedback;
    }

    public List<Feedback> getFeedbackForTask(Long taskId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new RuntimeException("Task not found");
        }
        return feedbackRepository.findByTask(optionalTask.get());
    }

    public List<Feedback> getPublicFeedbackForTask(Long taskId){
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isEmpty()){
            throw new RuntimeException("Task not found");
        }
        return feedbackRepository.findByTaskAndIsPublicTrue(optionalTask.get());
    }
}
