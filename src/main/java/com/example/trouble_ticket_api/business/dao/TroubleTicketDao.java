package com.example.trouble_ticket_api.business.dao;

import com.example.trouble_ticket_api.entity.TroubleTicket;

import java.util.List;
import java.util.Optional;

public interface TroubleTicketDao {
    Optional<TroubleTicket> findByExternalId(String externalId);

    Optional<TroubleTicket> findById(String id);

    List<TroubleTicket> findAll();

    void save(TroubleTicket newTicket);
}
