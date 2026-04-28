package com.clayton.ordermanagementapi.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record CreateUserRequest(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password
) { }
