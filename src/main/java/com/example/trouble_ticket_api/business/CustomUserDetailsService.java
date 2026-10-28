package com.example.trouble_ticket_api.business;

import com.example.trouble_ticket_api.business.dao.UserDao;
import com.example.trouble_ticket_api.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userDao.findByUsername(username).orElseThrow(() -> new NotFoundException("UZYTKOWNIK_NIE_ZNALEZIONY", "Nie znaleziono użytkownika z nazwą: " + username));
    }
}