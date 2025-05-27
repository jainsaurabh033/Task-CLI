package com.taskcli.task_manager.service;
import com.taskcli.task_manager.controller.SubtaskResponse;
import com.taskcli.task_manager.model.Subtask;
import com.taskcli.task_manager.repository.SubtaskRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository, TaskRepository taskRepository){
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
    }

    public Subtask createSubtask(Subtask subtask, Long taskId) {
        return taskRepository.findById(taskId).map(task -> {
            subtask.setTask(task);
            return subtaskRepository.save(subtask);
        }).orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }

    public List<SubtaskResponse> getSubtasksByTaskId(Long taskId) {
        List<Subtask> subtasks = subtaskRepository.findAll().stream().filter(subtask -> subtask.getTask()!=null
        && taskId.equals(subtask.getTask().getId())).toList();

        return subtasks.stream()
                .map(s -> new SubtaskResponse(s.isCompleted(), s.getId(), s.getTitle()))
                .collect(Collectors.toList());
    }

    public Subtask updateSubtaskStatus(Long subtaskId, boolean completed) {
        Optional<Subtask> optionalSubtask = subtaskRepository.findById(subtaskId);
        if (optionalSubtask.isPresent()) {
            Subtask subtask = optionalSubtask.get();
            subtask.setCompleted(completed);
            return subtaskRepository.save(subtask);
        } else {
            throw new RuntimeException("Subtask not found with id: " + subtaskId);
        }
    }

    public void deleteSubtask(Long subtaskId) {
        subtaskRepository.deleteById(subtaskId);
    }
}
