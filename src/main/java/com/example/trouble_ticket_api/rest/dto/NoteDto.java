package com.example.trouble_ticket_api.rest.dto;

import java.time.LocalDateTime;

public record NoteDto(
        String id,
        String text,
        LocalDateTime date
) {
}
