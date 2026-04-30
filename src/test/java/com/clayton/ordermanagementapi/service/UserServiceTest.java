package com.clayton.ordermanagementapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.clayton.ordermanagementapi.dto.CreateUserRequest;
import com.clayton.ordermanagementapi.dto.UserResponse;
import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.exception.EmailAlreadyExistsException;
import com.clayton.ordermanagementapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {

        CreateUserRequest request = new CreateUserRequest(
                "Clayton",
                "test@email.com",
                "123456"
        );

        when(userRepository.existsByEmail("test@email.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.createdUser(request);

        assertEquals("Clayton", response.name());
        assertEquals("test@email.com", response.email());
        assertEquals("CLIENT", response.role());

        verify(userRepository).existsByEmail("test@email.com");
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {

        CreateUserRequest request = new CreateUserRequest(
                "Clayton",
                "test@email.com",
                "123456"
        );

        when(userRepository.existsByEmail("test@email.com"))
                .thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () ->
                userService.createdUser(request)
        );

        verify(userRepository).existsByEmail("test@email.com");
        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }
}
