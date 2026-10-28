package com.example.trouble_ticket_api.domain.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends DomainException {
    public NotFoundException(final String code, final String message) {
        super(code, message);
    }
}
