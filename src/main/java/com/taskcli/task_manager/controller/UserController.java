package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.LoginRequest;
import com.taskcli.task_manager.dto.RegisterRequest;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.service.JwtService;
import com.taskcli.task_manager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        System.out.println("✅ Register API HIT");
        User savedUser = userService.registerUser(request);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            User user = userService.authenticateUser(request);
            String token = jwtService.generateToken(user.getEmail());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }
}
