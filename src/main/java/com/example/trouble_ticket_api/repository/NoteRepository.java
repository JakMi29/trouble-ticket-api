package com.example.trouble_ticket_api.repository;

import com.example.trouble_ticket_api.business.dao.NoteDao;
import com.example.trouble_ticket_api.entity.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class NoteRepository implements NoteDao {
    private final NoteJpaRepository repository;

    @Override
    public void save(Note note) {
        repository.save(note);
    }
}
