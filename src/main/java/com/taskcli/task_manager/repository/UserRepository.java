package com.taskcli.task_manager.repository;

import com.taskcli.task_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
