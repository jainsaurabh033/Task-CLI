// src/main/java/com/taskcli/task_manager/repository/SubtaskRepository.java
package com.taskcli.task_manager.repository;

import com.taskcli.task_manager.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {
}