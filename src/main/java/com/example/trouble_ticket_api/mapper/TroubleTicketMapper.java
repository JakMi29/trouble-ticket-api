package com.example.trouble_ticket_api.mapper;

import com.example.trouble_ticket_api.entity.Note;
import com.example.trouble_ticket_api.entity.TroubleTicket;
import com.example.trouble_ticket_api.rest.dto.NoteDto;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketDto;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketSummary;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TroubleTicketMapper {

    public TroubleTicketDto mapToDto(TroubleTicket ticket) {
        return new TroubleTicketDto(
                ticket.getId(),
                ticket.getExternalId(),
                ticket.getServiceId(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getNotes().stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    public TroubleTicketSummary mapToSummary(TroubleTicket ticket) {
        return new TroubleTicketSummary(
                ticket.getExternalId(),
                ticket.getServiceId(),
                ticket.getDescription(),
                ticket.getStatus());
    }

    public NoteDto mapToDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getText(),
                note.getDate());
    }
}
