package com.stephan.demo_spring_jwt.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailCustomService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            List<SimpleGrantedAuthority> roles = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            User user = new User("admin", "$2a$12$OxmCyq0cZIUPnDZA6WPoc.WeGeTTtBFF8tQZPrTrbCUJ5p5AaKUpe", roles);
            return user;
        }
        throw new UsernameNotFoundException("This user is not found: " + username);
    }
}
