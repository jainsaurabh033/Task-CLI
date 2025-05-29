package com.taskcli.task_manager.repository;

import com.taskcli.task_manager.model.Feedback;
import com.taskcli.task_manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByTask(Task task);
    List<Feedback> findByTaskAndPublicFeedbackTrue(Task task);

    Optional<Feedback> findByTaskAndPublicFeedbackFalse(Task task);
    int countByTask(Task task);

}
