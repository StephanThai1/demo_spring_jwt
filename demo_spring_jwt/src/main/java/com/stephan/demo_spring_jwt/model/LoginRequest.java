package com.stephan.demo_spring_jwt.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
