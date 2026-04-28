package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.dto.RegisterRequest;
import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.enums.Role;
import com.clayton.ordermanagementapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setRole(Role.CLIENT);

        userRepository.save(user);
    }
}
