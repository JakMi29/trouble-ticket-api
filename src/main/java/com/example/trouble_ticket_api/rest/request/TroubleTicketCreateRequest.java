package com.example.trouble_ticket_api.rest.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TroubleTicketCreateRequest {
    @NotBlank
    @Size(min = 1)
    private String externalId;
    @NotNull
    @Min(1)
    private Long serviceId;
    @NotBlank
    @Size(min = 1)
    private String description;
    @NotBlank
    private String status;
    @NotBlank
    @Size(min = 1)
    private String note;
}