package com.akatsuki.authorization.service;

import com.akatsuki.authorization.dto.UserDto;
import com.akatsuki.authorization.exception.BadRequestException;
import com.akatsuki.authorization.model.User;
import com.akatsuki.authorization.repository.UserRepository;
import com.akatsuki.authorization.service.impl.AuthorizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Test
    void createAuthorizationTestRuntimeException() {
        // Given
        Long id = 1L;
        // When - Then
        Assertions.assertThrows(BadRequestException.class, () -> authorizationService.getUser(id));
    }

    @Test
    void createAccommodationTest() {
        // Given
        UserDto userDto = new UserDto();
        User user = new User();

        when(userRepositoryMock.findByUsername(any())).thenReturn(Optional.empty());
        when(modelMapperMock.map(any(UserDto.class), eq(User.class))).thenReturn(user);

        userDto.setPassword("123");
        userDto.setRepeatPassword("123");
        // When
        authorizationService.createUser(userDto);

        // Then
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }
}
