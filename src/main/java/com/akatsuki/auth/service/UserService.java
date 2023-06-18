package com.akatsuki.auth.service;

import com.akatsuki.auth.dto.GetUserDto;
import com.akatsuki.auth.dto.UpdateUserDto;
import com.akatsuki.auth.dto.UserDto;
import com.akatsuki.auth.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    GetUserDto getUser(Long id);

    void createUser(UserDto userDto);

    void updateUser(UpdateUserDto userDto);

    void addCancellation(Long id);

    void deleteUser(Long id);
}
