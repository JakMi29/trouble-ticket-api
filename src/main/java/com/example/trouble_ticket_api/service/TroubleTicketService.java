package com.example.trouble_ticket_api.service;

import com.example.trouble_ticket_api.business.dao.TroubleTicketDao;
import com.example.trouble_ticket_api.domain.exception.BadRequestException;
import com.example.trouble_ticket_api.domain.exception.NotFoundException;
import com.example.trouble_ticket_api.entity.Note;
import com.example.trouble_ticket_api.entity.Status;
import com.example.trouble_ticket_api.entity.TroubleTicket;
import com.example.trouble_ticket_api.mapper.TroubleTicketMapper;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketDto;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketSummary;
import com.example.trouble_ticket_api.rest.request.TroubleTicketCloseRequest;
import com.example.trouble_ticket_api.rest.request.TroubleTicketCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TroubleTicketService {

    private final TroubleTicketDao dao;
    private final TroubleTicketMapper mapper;

    public TroubleTicketDto createOrGetTroubleTicket(TroubleTicketCreateRequest request) {
        return dao.findByExternalId(request.getExternalId())
                .map(mapper::mapToDto)
                .orElseGet(() -> {
                    TroubleTicket newTicket = TroubleTicket.builder()
                            .externalId(request.getExternalId())
                            .serviceId(request.getServiceId())
                            .description(request.getDescription())
                            .status(Status.NEW)
                            .build();

                    Note note = Note.builder()
                            .date(LocalDateTime.now())
                            .text(request.getNote())
                            .troubleTicket(newTicket)
                            .build();

                    newTicket.setNotes(List.of(note));

                    dao.save(newTicket);
                    return mapper.mapToDto(newTicket);
                });
    }

    public List<TroubleTicketSummary> listTroubleTickets() {
        return dao.findAll().stream()
                .map(mapper::mapToSummary)
                .toList();
    }

    public TroubleTicketDto getTroubleTicketDtoById(String id) {
        return dao.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new NotFoundException("TROUBLE_TICKET_NOT_FOUND", "Zgłoszenie nie istnieje albo nie jest widoczne w tenant scope użytkownika."));
    }

    public TroubleTicket getTroubleTicketById(String id) {
        return dao.findById(id)
                .orElseThrow(() -> new NotFoundException("TROUBLE_TICKET_NOT_FOUND", "Zgłoszenie nie istnieje albo nie jest widoczne w tenant scope użytkownika."));
    }

    public TroubleTicketDto closeTroubleTicket(String id, TroubleTicketCloseRequest request) {
        TroubleTicket ticket = dao.findById(id)
                .orElseThrow(() -> new NotFoundException("SERVICE_NOT_FOUND", "Zgłoszenie nie istnieje albo nie jest widoczne w tenant scope użytkownika."));
        if (!Status.CLOSED.equals(request.status())) {
            throw new BadRequestException("VALIDATION_ERROR", "Pole status ma niedozwoloną wartość dla tej operacji.");
        }
        ticket.setStatus(Status.CLOSED);
        dao.save(ticket);
        return mapper.mapToDto(ticket);
    }


}
