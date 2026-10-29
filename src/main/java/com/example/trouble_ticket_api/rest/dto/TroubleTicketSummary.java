package com.example.trouble_ticket_api.rest.dto;

import com.example.trouble_ticket_api.entity.Status;

public record TroubleTicketSummary(
        String externalId,
        Long serviceId,
        String description,
        Status status) {
}
