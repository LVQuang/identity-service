package dev.quang.identity_service.Configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.quang.identity_service.Entity.User;
import dev.quang.identity_service.Enums.Role;
import dev.quang.identity_service.Respository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {
    @NonFinal
    @Value("${meta.admin-email}")
    String ADMIN_EMAIL;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
                Set<String> roles = new HashSet<>();
                roles.add(Role.ADMIN.name());
                var user = User.builder()
                    .email(ADMIN_EMAIL)
                    .name("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .firstname("Le")
                    .lastname("Quang")
                    .dob(LocalDate.of(2003, 01, 10))
                    .roles(roles)
                    .build();
                userRepository.save(user);
                log.info("Admin created successfully");
            }
        };
    }
}
