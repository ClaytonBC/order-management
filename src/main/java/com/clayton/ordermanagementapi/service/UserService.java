package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.CreateUserRequest;
import com.clayton.ordermanagementapi.dto.UserResponse;
import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.enums.Role;
import com.clayton.ordermanagementapi.exception.EmailAlreadyExistsException;
import com.clayton.ordermanagementapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createdUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.CLIENT);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );
    }
}
