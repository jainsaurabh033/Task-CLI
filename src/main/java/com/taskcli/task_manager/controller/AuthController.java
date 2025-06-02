package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.dto.RequestDTO.AuthRequest;
import com.taskcli.task_manager.dto.RequestDTO.RegisterRequest;
import com.taskcli.task_manager.dto.ResponseDTO.LoginResponse;
import com.taskcli.task_manager.dto.ResponseDTO.RegisterResponse;
import com.taskcli.task_manager.service.AuthService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController{

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) throws BadRequestException {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody AuthRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}