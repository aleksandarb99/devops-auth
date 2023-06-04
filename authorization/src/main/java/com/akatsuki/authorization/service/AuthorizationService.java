package com.akatsuki.authorization.service;

import com.akatsuki.authorization.dto.GetUserDto;
import com.akatsuki.authorization.dto.UpdateUserDto;
import com.akatsuki.authorization.dto.UserDto;

public interface AuthorizationService {
    void createUser(UserDto userDto);
    void deleteUser(Long id);
    void updateUser(UpdateUserDto userDto);
    GetUserDto getUser(Long id);
}
