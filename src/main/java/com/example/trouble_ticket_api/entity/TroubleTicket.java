package com.example.trouble_ticket_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trouble_ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TroubleTicket {

    @Id
    @Column(nullable = false, length = 255)
    private String id;

    @Column(name = "external_id", nullable = false, unique = true, length = 255)
    @Size(min = 1)
    private String externalId;

    @Column(name = "service_id", nullable = false)
    @Min(1)
    private Long serviceId;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    @Size(min = 1)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @NotNull
    private Status status;

    @OneToMany(mappedBy = "troubleTicket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Note> notes = new ArrayList();

    @PrePersist
    public void prePersist() {
        if (this.id == null || this.id.isBlank()) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }

}