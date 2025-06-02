package com.taskcli.task_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private boolean publicFeedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "given_by")
    @JsonIgnore
    private User manager;
    private LocalDate givenAt = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @JsonIgnore
    private User receiver;
}
