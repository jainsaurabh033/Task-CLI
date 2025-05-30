package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.PersonalAnalyticsResponseDTO;
import com.taskcli.task_manager.service.PersonalAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final PersonalAnalyticsService analyticsService;

    public AnalyticsController(PersonalAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/personal")
    public ResponseEntity<PersonalAnalyticsResponseDTO> getPersonalAnalytics(@RequestParam Long userId){
        PersonalAnalyticsResponseDTO response = analyticsService.getUserAnalytics(userId);
        return ResponseEntity.ok(response);
    }
}
