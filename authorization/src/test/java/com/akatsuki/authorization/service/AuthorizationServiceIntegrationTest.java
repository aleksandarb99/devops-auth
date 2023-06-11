package com.akatsuki.authorization.service;

import com.akatsuki.authorization.dto.UserDto;
import com.akatsuki.authorization.exception.BadRequestException;
import com.akatsuki.authorization.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

@SpringBootTest
@Testcontainers(parallel = true)
class AuthorizationServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", db::getJdbcUrl);
        registry.add("spring.datasource.username", db::getUsername);
        registry.add("spring.datasource.password", db::getPassword);
    }

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createAuthorizationTestRuntimeException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setGivenName("Marija");
        userDto.setLastName("Stojanovic");

        // When - Then
        Assertions.assertThrows(BadRequestException.class, () -> authorizationService.createUser(userDto));
    }

    @Test
    void createAuthorizationTest() {
        // Given
        UserDto userDto = UserDto.builder()
                .givenName("Marija")
                .lastName("Stojanovic")
                .address("Ulica Kralja Petra I, Nis")
                .username("marija")
                .password("123")
                .repeatPassword("123")
                .email("marija@gmail.com")
                .role("Guest")
                .phoneNumber("0644963258")
                .build();

        // When
        authorizationService.createUser(userDto);

        // Then
        Assertions.assertEquals(4, userRepository.count());
        Assertions.assertTrue(userRepository.findByUsername(userDto.getUsername()).isPresent());
    }

}
