package com.taskcli.task_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
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

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    private Integer rating;

    public LocalDate getGivenAt() {
        return givenAt;
    }

    public void setGivenAt(LocalDate givenAt) {
        this.givenAt = givenAt;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User givenBy) {
        this.manager = givenBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPublicFeedback() {
        return publicFeedback;
    }

    public void setPublicFeedback(boolean publicFeedback) {
        this.publicFeedback = publicFeedback;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
