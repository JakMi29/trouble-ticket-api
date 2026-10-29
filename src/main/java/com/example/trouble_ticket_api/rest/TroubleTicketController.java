package com.example.trouble_ticket_api.rest;

import com.example.trouble_ticket_api.rest.dto.TroubleTicketDto;
import com.example.trouble_ticket_api.rest.dto.TroubleTicketSummary;
import com.example.trouble_ticket_api.rest.request.TroubleTicketCloseRequest;
import com.example.trouble_ticket_api.rest.request.TroubleTicketCreateRequest;
import com.example.trouble_ticket_api.service.TroubleTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/troubleTicket")
@RequiredArgsConstructor
public class TroubleTicketController {

    private final TroubleTicketService troubleTicketService;


    @PostMapping
    public ResponseEntity<TroubleTicketDto> createTroubleTicket(
            @Valid @RequestBody TroubleTicketCreateRequest request,
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {

        TroubleTicketDto result = troubleTicketService.createOrGetTroubleTicket(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();

        return ResponseEntity.status(201)
                .header("Location", location.toString())
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<TroubleTicketSummary>> listTroubleTickets(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader) {
        List<TroubleTicketSummary> summaries = troubleTicketService.listTroubleTickets();
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TroubleTicketDto> getTroubleTicketById(
            @PathVariable("id") String id) {
        TroubleTicketDto ticket = troubleTicketService.getTroubleTicketDtoById(id);
        return ResponseEntity.ok(ticket);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TroubleTicketDto> closeTroubleTicket(
            @PathVariable("id") String id,
            @Valid @RequestBody TroubleTicketCloseRequest request) {
        TroubleTicketDto closedTicket = troubleTicketService.closeTroubleTicket(id, request);
        return ResponseEntity.ok(closedTicket);
    }
}