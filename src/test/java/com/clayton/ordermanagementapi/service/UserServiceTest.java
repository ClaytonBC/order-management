package com.clayton.ordermanagementapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.clayton.ordermanagementapi.dto.CreateUserRequest;
import com.clayton.ordermanagementapi.dto.UpdateUserRequest;
import com.clayton.ordermanagementapi.dto.UserResponse;
import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.enums.Role;
import com.clayton.ordermanagementapi.exception.EmailAlreadyExistsException;
import com.clayton.ordermanagementapi.exception.ResourceNotFoundException;
import com.clayton.ordermanagementapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

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

    @Test
    void shouldReturnAllUsers() {

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Clayton");
        user1.setEmail("clayton@email.com");
        user1.setRole(Role.CLIENT);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("John");
        user2.setEmail("john@email.com");
        user2.setRole(Role.CLIENT);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponse> response = userService.getAllUsers();

        assertEquals(2, response.size());
        assertEquals("Clayton", response.get(0).name());

        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserById() {

        User user = new User();
        user.setId(1L);
        user.setName("Clayton");
        user.setEmail("test@email.com");
        user.setRole(Role.CLIENT);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertEquals("Clayton", response.name());
        assertEquals("test@email.com", response.email());

        verify(userRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenUserNotFoundById() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.getUserById(1L)
        );

        verify(userRepository).findById(1L);
    }

    @Test
    void shouldDeleteUserSuccessfully() {

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.deleteUser(1L)
        );

        verify(userRepository).findById(1L);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void shouldUpdateUserSuccessfully() {

        User user = new User();
        user.setId(1L);
        user.setName("Old Name");
        user.setEmail("old@email.com");
        user.setPassword("old-pass");
        user.setRole(Role.CLIENT);

        UpdateUserRequest request = new UpdateUserRequest(
                "New Name",
                "new@email.com",
                "123456"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("123456")).thenReturn("encoded-pass");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserResponse response = userService.updateUser(1L, request);

        assertEquals("New Name", response.name());
        assertEquals("new@email.com", response.email());

        verify(userRepository).save(user);
    }

    @Test
    void shouldUpdateUserPartially() {

        User user = new User();
        user.setId(1L);
        user.setName("Old Name");
        user.setEmail("old@email.com");
        user.setPassword("old-pass");
        user.setRole(Role.CLIENT);

        UpdateUserRequest request = new UpdateUserRequest(
                "New Name",
                null,
                null
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.updateUser(1L, request);

        assertEquals("New Name", response.name());
        assertEquals("old@email.com", response.email());

        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingUser() {

        UpdateUserRequest request = new UpdateUserRequest(
                "Name",
                "email@email.com",
                "123"
        );

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                userService.updateUser(1L, request)
        );

        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any());
    }
}
