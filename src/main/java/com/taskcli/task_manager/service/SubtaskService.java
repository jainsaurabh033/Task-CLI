package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.model.Subtask;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.repository.SubtaskRepository;
import com.taskcli.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository, TaskRepository taskRepository){
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
    }

    public Subtask markSubtaskAsComplete(Long subtaskId){
        Subtask subtask = subtaskRepository.findById(subtaskId).orElseThrow(()-> new RuntimeException("Subtask not found"));

        if(subtask.isCompleted()){
            throw new IllegalStateException("Subtask is already marked complete.");
        }

        subtask.setCompleted(true);
        subtaskRepository.save(subtask);

        Task parentTask = subtask.getTask();
        List<Subtask> allSubtasks = parentTask.getSubtasks();

        long completedCount = allSubtasks.stream().filter(Subtask::isCompleted).count();

        if(completedCount == allSubtasks.size()){
            parentTask.setStatus(TaskStatus.COMPLETED);
        }
        else{
            parentTask.setStatus(TaskStatus.IN_PROGRESS);
        }

        taskRepository.save(parentTask);
        return subtask;
    }
}
