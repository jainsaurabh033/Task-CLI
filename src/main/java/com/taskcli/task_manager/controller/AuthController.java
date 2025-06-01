package com.taskcli.task_manager.controller;

import com.taskcli.task_manager.Enum.Role;
import com.taskcli.task_manager.dto.AuthRequest;
import com.taskcli.task_manager.dto.AuthResponse;
import com.taskcli.task_manager.dto.RegisterRequest;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.UserRepository;
import com.taskcli.task_manager.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            Role role = Role.valueOf(request.getRole().toUpperCase());

            User user = new User();
            user.setEmail(request.getEmail());
            user.setName(request.getName());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(role);

            userRepository.save(user);
            return ResponseEntity.ok("Registered");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role. Allowed values: MEMBER, MANAGER");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    request.getEmail(), "",
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
            );

            String token = jwtUtil.generateToken(userDetails);

            AuthResponse response = new AuthResponse(
                    user.getEmail(),
                    user.getId(),
                    user.getName(),
                    user.getRole(),
                    token
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
