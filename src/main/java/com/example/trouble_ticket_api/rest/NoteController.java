package com.example.trouble_ticket_api.rest;

import com.example.trouble_ticket_api.rest.dto.NoteDto;
import com.example.trouble_ticket_api.rest.request.NoteCreateRequest;
import com.example.trouble_ticket_api.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/troubleTicket/{id}/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto> addTroubleTicketNote(
            @PathVariable("id") String troubleTicketId,
            @Valid @RequestBody NoteCreateRequest request) {
        NoteDto createdNote = noteService.addNoteToTicket(troubleTicketId, request);
        return ResponseEntity.status(201).body(createdNote);
    }
}