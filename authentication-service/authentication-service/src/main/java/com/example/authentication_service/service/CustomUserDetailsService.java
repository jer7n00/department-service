package com.example.authentication_service.service;


import com.example.authentication_service.entity.UserCredentialsEntity;
import com.example.authentication_service.repository.UserCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserCredentialsRepository userCredentialsRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentialsEntity> user = userCredentialsRepo.findByName(username);
//        System.out.println("user 2: " + user);
        return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("Username/password not valid!"));
    }
}
