package com.example.trouble_ticket_api.repository;

import com.example.trouble_ticket_api.business.dao.UserDao;
import com.example.trouble_ticket_api.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserDao {
    private final UserJpaRepository repository;

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
