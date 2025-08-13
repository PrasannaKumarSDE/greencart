package com.example.greencart.security;

import com.example.greencart.model.User;
import com.example.greencart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // map role string (e.g. ROLE_MANAGER) to authorities
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(u.getRole() == null ? "ROLE_MANAGER" : u.getRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPasswordHash(),
                authorities
        );
    }
}
