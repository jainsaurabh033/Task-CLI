package com.taskcli.task_manager.repository;

import com.taskcli.task_manager.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
