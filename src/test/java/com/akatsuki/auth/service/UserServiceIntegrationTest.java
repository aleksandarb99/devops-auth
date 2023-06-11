package com.akatsuki.auth.service;

import com.akatsuki.auth.dto.UserDto;
import com.akatsuki.auth.enums.UserRole;
import com.akatsuki.auth.exception.BadRequestException;
import com.akatsuki.auth.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers(parallel = true)
class UserServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", db::getJdbcUrl);
        registry.add("spring.datasource.username", db::getUsername);
        registry.add("spring.datasource.password", db::getPassword);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createAuthorizationTestIllegalArgumentException() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setGivenName("Marija");
        userDto.setLastName("Stojanovic");

        // When - Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(userDto));
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
                .role(UserRole.GUEST)
                .phoneNumber("0644963258")
                .build();

        // When
        userService.createUser(userDto);

        // Then
        Assertions.assertEquals(4, userRepository.count());
        Assertions.assertTrue(userRepository.findByUsername(userDto.getUsername()).isPresent());
    }

}
