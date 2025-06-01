package com.taskcli.task_manager.dto;

import com.taskcli.task_manager.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;


public class AuthResponse {
    private String token;
    private Long id;
    private String name;
    private String email;
    private Role role;

    public AuthResponse(String email, Long id, String name, Role role, String token) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.role = role;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
