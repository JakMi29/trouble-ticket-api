package com.example.trouble_ticket_api.rest.request;

import com.example.trouble_ticket_api.entity.Status;

public record TroubleTicketCloseRequest(
        Status status) {
}
