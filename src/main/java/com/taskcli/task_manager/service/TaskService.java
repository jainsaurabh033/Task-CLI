package com.taskcli.task_manager.service;

import com.taskcli.task_manager.DTO.TaskCreateRequest;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.TaskRepository;
import com.taskcli.task_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
//@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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
}
