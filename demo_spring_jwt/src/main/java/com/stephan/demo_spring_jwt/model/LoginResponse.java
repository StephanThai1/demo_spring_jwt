package com.stephan.demo_spring_jwt.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwtToken;
}
