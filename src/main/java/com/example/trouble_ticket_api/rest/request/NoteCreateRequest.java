package com.example.trouble_ticket_api.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteCreateRequest {
    @NotBlank
    @Size(min = 1)
    private String text;
}
