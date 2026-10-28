package com.example.trouble_ticket_api.configuration.security;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {

    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String REQUEST_ID_ATTRIBUTE = "requestId";
    private static final String DEFAULT_REQUEST_ID = "unknown-request";

    public static String getRequestId(HttpServletRequest request) {
        if (request == null) {
            return DEFAULT_REQUEST_ID;
        }

        String requestId = request.getHeader(REQUEST_ID_HEADER);

        if (requestId == null || requestId.isBlank()) {
            Object attributeId = request.getAttribute(REQUEST_ID_ATTRIBUTE);
            if (attributeId != null) {
                requestId = attributeId.toString();
            }
        }

        if (requestId == null || requestId.isBlank()) {
            return DEFAULT_REQUEST_ID;
        }

        return requestId.trim();
    }
}