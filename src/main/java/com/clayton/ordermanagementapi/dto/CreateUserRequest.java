package com.clayton.ordermanagementapi.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record CreateUserRequest(

        @NotBlank
        @Schema(example = "Clayton")
        String name,

        @NotBlank
        @Email
        @Schema(example = "admin@email.com")
        String email,

        @NotBlank
        @Schema(example = "123456")
        String password
) { }
