package com.example.trouble_ticket_api.business;

import com.example.trouble_ticket_api.business.dao.UserDao;
import com.example.trouble_ticket_api.configuration.security.JwtService;
import com.example.trouble_ticket_api.domain.exception.NotFoundException;
import com.example.trouble_ticket_api.rest.request.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public String authenticate(AuthenticationRequest request) {
        String username = request.username();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        request.password()
                )
        );
        var user = userDao.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("UZYTKOWNIK_NIE_ZNALEZIONY", "Nie znaleziono użytkownika z nazwą: " + username));
        return jwtService.generateToken(user);
    }

}