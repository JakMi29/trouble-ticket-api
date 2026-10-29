package com.example.trouble_ticket_api.mapper;

import com.example.trouble_ticket_api.entity.Note;
import com.example.trouble_ticket_api.rest.dto.NoteDto;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteDto mapToDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getText(),
                note.getDate());
    }
}
