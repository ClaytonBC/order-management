package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateUserRequest;
import com.clayton.ordermanagementapi.dto.UserResponse;
import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.enums.Role;
import com.clayton.ordermanagementapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createdUser(CreateUserRequest request){

        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.CLIENT);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole().name());

        return response;
    }
}
