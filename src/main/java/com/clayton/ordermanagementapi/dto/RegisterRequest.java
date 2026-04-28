package com.clayton.ordermanagementapi.dto;

public record RegisterRequest(

        String name,
        String email,
        String password
) {
}
