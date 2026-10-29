package com.example.trouble_ticket_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @Column(nullable = false, length = 255)
    private String id;


    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 1)
    private String text;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trouble_ticket_id", nullable = false)
    @NotNull
    private TroubleTicket troubleTicket;

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isBlank()) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }
}