package com.example.trouble_ticket_api.service;

import com.example.trouble_ticket_api.business.dao.NoteDao;
import com.example.trouble_ticket_api.entity.Note;
import com.example.trouble_ticket_api.entity.TroubleTicket;
import com.example.trouble_ticket_api.mapper.NoteMapper;
import com.example.trouble_ticket_api.rest.dto.NoteDto;
import com.example.trouble_ticket_api.rest.request.NoteCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteDao noteDao;
    private final NoteMapper mapper;
    private final TroubleTicketService troubleTicketService;

    public NoteDto addNoteToTicket(String troubleTicketId, NoteCreateRequest request) {
        TroubleTicket troubleTicket = troubleTicketService.getTroubleTicketById(troubleTicketId);
        Note note = Note.builder()
                .troubleTicket(troubleTicket)
                .text(request.getText())
                .date(LocalDateTime.now())
                .build();
        noteDao.save(note);
        return mapper.mapToDto(note);
    }
}
