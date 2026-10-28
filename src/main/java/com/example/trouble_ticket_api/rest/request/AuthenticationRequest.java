package com.example.trouble_ticket_api.rest.request;

import lombok.NonNull;

public record AuthenticationRequest(
        @NonNull String username,
        @NonNull String password
) {
}