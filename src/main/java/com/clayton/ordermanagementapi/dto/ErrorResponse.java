package com.clayton.ordermanagementapi.dto;

public record ErrorResponse(
        int status,
        String message,
        String timestamp
) { }
