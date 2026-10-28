package com.example.trouble_ticket_api.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    protected String code;

    public DomainException(final String code, final String message) {
        super(message);
        this.code = code;
    }

}
