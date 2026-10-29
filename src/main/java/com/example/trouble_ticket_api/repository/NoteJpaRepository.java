package com.example.trouble_ticket_api.repository;

import com.example.trouble_ticket_api.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepository extends JpaRepository<Note, String> {
}
