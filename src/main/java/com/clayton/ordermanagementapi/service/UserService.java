package com.clayton.ordermanagementapi.service;

import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.enums.Role;
import com.clayton.ordermanagementapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createdUser(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already registered");
        }

        user.setRole(Role.CLIENT);

        return userRepository.save(user);
    }
}
