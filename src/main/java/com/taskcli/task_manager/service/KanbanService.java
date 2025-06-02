package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.TaskStatus;
import com.taskcli.task_manager.dto.ResponseDTO.KanbanTaskResponseDTO;
import com.taskcli.task_manager.model.Task;
import com.taskcli.task_manager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KanbanService {

    private final TaskRepository taskRepository;

    public KanbanService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Map<String, List<KanbanTaskResponseDTO>> getKanbanBySprint(Long sprintId){
        List<Task> tasks = taskRepository.findBySprintId(sprintId);

        Map<String, List<KanbanTaskResponseDTO>> kanban = new HashMap<>();
        for(TaskStatus status : TaskStatus.values()){
            kanban.put(status.name(), new ArrayList<>());
        }

        for(Task task : tasks){
            KanbanTaskResponseDTO dto = new KanbanTaskResponseDTO();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            dto.setStatus(task.getStatus().name());
            dto.setOwnerName(task.getCreator().getName());
            kanban.get(task.getStatus().name()).add(dto);
        }

        return kanban;
    }
}
