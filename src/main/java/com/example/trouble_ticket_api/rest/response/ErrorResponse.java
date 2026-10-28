package com.example.trouble_ticket_api.rest.response;

public record ErrorResponse(String code, String message, String requestId) {
}