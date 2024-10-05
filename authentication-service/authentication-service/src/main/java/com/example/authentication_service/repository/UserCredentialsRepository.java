package com.example.authentication_service.repository;

import com.example.authentication_service.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity,Long> {
    public Optional<UserCredentialsEntity> findByName(String name);
}
