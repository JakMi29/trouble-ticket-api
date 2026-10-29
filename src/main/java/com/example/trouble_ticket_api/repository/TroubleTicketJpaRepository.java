package com.example.trouble_ticket_api.repository;

import com.example.trouble_ticket_api.entity.TroubleTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TroubleTicketJpaRepository extends JpaRepository<TroubleTicket, String> {
    Optional<TroubleTicket> findByExternalId(String externalId);
}
