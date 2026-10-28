package com.example.trouble_ticket_api.business.dao;

import com.example.trouble_ticket_api.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByUsername(String username);
}
