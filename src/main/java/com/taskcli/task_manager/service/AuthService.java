package com.taskcli.task_manager.service;

import com.taskcli.task_manager.Enum.Role;
import com.taskcli.task_manager.dto.RequestDTO.AuthRequest;
import com.taskcli.task_manager.dto.RequestDTO.RegisterRequest;
import com.taskcli.task_manager.dto.ResponseDTO.AuthResponse;
import com.taskcli.task_manager.dto.ResponseDTO.SuccessLoginResponse;
import com.taskcli.task_manager.dto.ResponseDTO.SuccessRegisterResponse;
import com.taskcli.task_manager.exception.Custom.InvalidCredentialsException;
import com.taskcli.task_manager.exception.Custom.UserAlreadyExistsException;
import com.taskcli.task_manager.model.User;
import com.taskcli.task_manager.repository.UserRepository;
import com.taskcli.task_manager.security.JwtUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                       PasswordEncoder passwordEncoder, UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public SuccessRegisterResponse register(RegisterRequest request) throws BadRequestException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Email already registered: " + request.getEmail());
        }

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role. Allowed values: MEMBER or MANAGER.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        return new SuccessRegisterResponse("Registered successfully");
    }

    public SuccessLoginResponse login(AuthRequest request){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmail(), request.getPassword()
            ));
        } catch(Exception e){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new InvalidCredentialsException("User not found"));

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                request.getEmail(), "", List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

        String token = jwtUtil.generateToken(userDetails);

        return new SuccessLoginResponse(
                user.getEmail(),
                user.getId(),
                user.getName(),
                user.getRole(),
                token
        );
    }
}
