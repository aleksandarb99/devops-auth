package com.akatsuki.auth.config;

import com.akatsuki.auth.enums.UserRole;
import com.akatsuki.auth.model.User;
import com.akatsuki.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DbInitializationStartupRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        User u1 = User.builder()
                .username("marko")
                .givenName("Marko")
                .lastName("Markovic")
                .address("Vladike Nikolaja 2")
                .email("marko@gmail.com")
                .phoneNumber("063333333")
                .role(UserRole.HOST)
                .password(passwordEncoder.encode("marko"))
                .numberOfCancelledReservations(0)
                .build();
        User u2 = User.builder()
                .username("nikola")
                .givenName("Nikola")
                .lastName("Nikolic")
                .address("Vladike Nikolaja 3")
                .email("nikola@gmail.com")
                .phoneNumber("061111111")
                .role(UserRole.HOST)
                .password(passwordEncoder.encode("nikola"))
                .numberOfCancelledReservations(0)
                .build();
        User u3 = User.builder()
                .username("jovan")
                .givenName("Jovan")
                .lastName("Jovanovic")
                .address("Vladike Nikolaja 3")
                .email("jovan@gmail.com")
                .phoneNumber("062222222")
                .role(UserRole.GUEST)
                .password(passwordEncoder.encode("jovan"))
                .numberOfCancelledReservations(0)
                .build();

        userRepository.saveAll(List.of(u1, u2, u3));
    }
}
