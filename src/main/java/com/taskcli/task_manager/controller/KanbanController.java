package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.KanbanTaskResponseDTO;
import com.taskcli.task_manager.service.KanbanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/kanban")
public class KanbanController {
    private final KanbanService kanbanService;

    public KanbanController(KanbanService kanbanService){
        this.kanbanService = kanbanService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<KanbanTaskResponseDTO>>> getKanbanBoard(@RequestParam Long sprintId){
        return ResponseEntity.ok(kanbanService.getKanbanBySprint(sprintId));
    }

}
