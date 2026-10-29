package com.example.trouble_ticket_api.domain.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends DomainException {
    public BadRequestException(final String code, final String message) {
        super(code, message);
    }
}
