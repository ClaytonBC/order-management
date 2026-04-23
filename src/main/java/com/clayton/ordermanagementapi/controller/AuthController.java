package com.clayton.ordermanagementapi.controller;

import com.clayton.ordermanagementapi.dto.LoginRequest;
import com.clayton.ordermanagementapi.dto.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return new LoginResponse("Login sucessful");
    }

}
