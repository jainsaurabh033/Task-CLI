package com.taskcli.task_manager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
