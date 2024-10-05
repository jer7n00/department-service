package com.example.authentication_service.service;

import com.example.authentication_service.entity.UserCredentialsEntity;
import com.example.authentication_service.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserCredentialsService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserCredentialsRepository authRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserCredentialsEntity register(UserCredentialsEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return authRepo.saveAndFlush(user);
    }
    public String generateToken(String name) {
        return jwtService.generateToken(name);
    }
    public boolean verifyToken(String token) {
        jwtService.validateToken(token);
        return true;
    }
}
