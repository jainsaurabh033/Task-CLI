package com.taskcli.task_manager.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id){
        super("User not found with ID: " + id);
    }
}
