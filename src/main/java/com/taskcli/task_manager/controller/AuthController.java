package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.RateLimiter.RequestLimiter;
import com.taskcli.task_manager.dto.RequestDTO.AuthRequest;
import com.taskcli.task_manager.dto.RequestDTO.RegisterRequest;
import com.taskcli.task_manager.dto.ResponseDTO.LoginResponse;
import com.taskcli.task_manager.dto.ResponseDTO.RegisterResponse;
import com.taskcli.task_manager.dto.ResponseDTO.UserProfileResponse;
import com.taskcli.task_manager.service.AuthService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController{

    private final AuthService authService;
    private final RequestLimiter requestLimiter;

    public AuthController(AuthService authService, RequestLimiter requestLimiter){
        this.authService = authService;
        this.requestLimiter = requestLimiter;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) throws BadRequestException {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody AuthRequest request){

        if (!requestLimiter.isAllowed(request.getEmail())) {
            System.out.println("RateLimiter hit");
            return ResponseEntity.status(429).body(null);
        }

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(){
        UserProfileResponse response = authService.getCurrentUserProfile();
        return ResponseEntity.ok(response);
    }
}