package com.clayton.ordermanagementapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(

        @Schema(example = "user@email.com")
        String email,

        @Schema(example = "123456")
        String password
) { }
