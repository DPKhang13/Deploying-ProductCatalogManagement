package com.example.project.config;

import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
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
        if (userRepository.findByEmail("test@gmail.com").isEmpty()) {

            User testUser = new User();
            testUser.setEmail("test@gmail.com");
            testUser.setPassword(passwordEncoder.encode("1234"));
            testUser.setRole(Role.ADMIN);
            testUser.setIsActive(true);
            testUser.setFullName("Admin Test");
            testUser.setPhone("0123456789");
            testUser.setAddress("Hà Nội");

            userRepository.save(testUser);
            System.out.println("✅ Created test user: test@gmail.com / 1234");
        }
    }

}
