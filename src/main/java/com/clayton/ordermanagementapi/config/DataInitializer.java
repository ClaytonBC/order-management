package com.clayton.ordermanagementapi.config;

import com.clayton.ordermanagementapi.entity.User;
import com.clayton.ordermanagementapi.enums.Role;
import com.clayton.ordermanagementapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@demo.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin Demo");
            admin.setEmail("admin@demo.com");
            admin.setPassword(passwordEncoder.encode("demo123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        if (userRepository.findByEmail("client@demo.com").isEmpty()) {
            User client = new User();
            client.setName("Client Demo");
            client.setEmail("client@demo.com");
            client.setPassword(passwordEncoder.encode("demo123"));
            client.setRole(Role.CLIENT);
            userRepository.save(client);
        }
    }
}