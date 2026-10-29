package com.example.trouble_ticket_api.repository;

import com.example.trouble_ticket_api.business.dao.TroubleTicketDao;
import com.example.trouble_ticket_api.entity.TroubleTicket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TroubleTicketRepository implements TroubleTicketDao {
    private final TroubleTicketJpaRepository repository;

    @Override
    public Optional<TroubleTicket> findByExternalId(String externalId) {
        return repository.findByExternalId(externalId);
    }

    @Override
    public Optional<TroubleTicket> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<TroubleTicket> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(TroubleTicket newTicket) {
        repository.save(newTicket);
    }
}
