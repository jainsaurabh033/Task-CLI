package com.taskcli.task_manager.service;

import com.taskcli.task_manager.exception.UserNotFoundException;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(String email){
        User user = new User();
        user.setEmail(email);
        return userRepository.save(user);
    }

    @Transactional
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
