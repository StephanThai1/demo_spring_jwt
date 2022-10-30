package com.stephan.demo_spring_jwt.controller;

import com.stephan.demo_spring_jwt.jwt.JwtTokenProvider;
import com.stephan.demo_spring_jwt.model.LoginRequest;
import com.stephan.demo_spring_jwt.model.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails customUserDetails = (UserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(customUserDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwt);
        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/random")
    public ResponseEntity<String> randomStuff() {
        return ResponseEntity.ok("This is good");
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok("logout success");
    }
}
