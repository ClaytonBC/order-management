package com.clayton.ordermanagementapi.dto;

public record LoginRequest(
        String email,
        String password
) { }
