package com.clayton.ordermanagementapi.controller;

import com.clayton.ordermanagementapi.dto.CreateUserRequest;
import com.clayton.ordermanagementapi.dto.UserResponse;
import com.clayton.ordermanagementapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(
            @RequestBody @Valid CreateUserRequest request){
        return userService.createdUser(request);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}
