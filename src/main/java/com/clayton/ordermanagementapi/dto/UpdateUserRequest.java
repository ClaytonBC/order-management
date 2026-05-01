package com.clayton.ordermanagementapi.dto;

public record UpdateUserRequest(
        String name,
        String email,
        String password
) { }
