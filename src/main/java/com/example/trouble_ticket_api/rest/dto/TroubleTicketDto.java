package com.example.trouble_ticket_api.rest.dto;

import com.example.trouble_ticket_api.entity.Status;

import java.util.List;

public record TroubleTicketDto(
        String id,
        String externalId,
        Long serviceId,
        String description,
        Status status,
        List<NoteDto> notes) {
}
