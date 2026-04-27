package com.clayton.ordermanagementapi.controller;

import com.clayton.ordermanagementapi.dto.LoginRequest;
import com.clayton.ordermanagementapi.dto.LoginResponse;
import com.clayton.ordermanagementapi.dto.RegisterRequest;
import com.clayton.ordermanagementapi.service.AuthService;
import com.clayton.ordermanagementapi.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getEmail());

        return new LoginResponse(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request
    ) {
        authService.register(request);

        return ResponseEntity.ok("User created successfully");
    }
}