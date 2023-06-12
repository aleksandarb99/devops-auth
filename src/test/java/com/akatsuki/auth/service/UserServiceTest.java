package com.akatsuki.auth.service;

import com.akatsuki.auth.dto.UserDto;
import com.akatsuki.auth.exception.BadRequestException;
import com.akatsuki.auth.model.User;
import com.akatsuki.auth.repository.UserRepository;
import com.akatsuki.auth.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @Mock
    private PasswordEncoder passwordEncoderMock;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUserTest() {
        // Given
        UserDto userDto = new UserDto();
        User user = new User();

        when(userRepositoryMock.findByUsername(any())).thenReturn(Optional.empty());
        when(modelMapperMock.map(any(UserDto.class), eq(User.class))).thenReturn(user);

        userDto.setPassword("123");
        userDto.setRepeatPassword("123");

        // When
        userService.createUser(userDto);

        // Then
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

    @Test
    void createUserTestBadRequestException() {
        // Given
        UserDto userDto = new UserDto();
        User user = new User();

        when(userRepositoryMock.findByUsername(any())).thenReturn(Optional.of(user));

        // When - Then
        Assertions.assertThrows(BadRequestException.class, () -> userService.createUser(userDto));
    }
}
