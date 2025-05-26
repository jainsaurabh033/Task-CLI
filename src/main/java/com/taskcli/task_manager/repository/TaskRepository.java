package com.taskcli.task_manager.repository;

import com.taskcli.task_manager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t JOIN FETCH t.creator c WHERE c.id = :userId")
    Page<Task> findByCreatorIdWithUser(Long userId, Pageable pageable);
}