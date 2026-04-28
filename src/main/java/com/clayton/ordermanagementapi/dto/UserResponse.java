package com.clayton.ordermanagementapi.dto;

public record UserResponse(

        Long id,
        String name,
        String email,
        String role

) { }
