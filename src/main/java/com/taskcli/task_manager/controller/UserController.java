package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.RegisterRequest;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        System.out.println("✅ Register API HIT");
        User savedUser = userService.registerUser(request);
        return ResponseEntity.ok(savedUser);
    }
}
