package com.example.trouble_ticket_api.repository;

import com.example.trouble_ticket_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}