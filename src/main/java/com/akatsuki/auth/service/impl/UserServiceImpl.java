package com.akatsuki.auth.service.impl;

import com.akatsuki.auth.dto.GetUserDto;
import com.akatsuki.auth.dto.UpdateUserDto;
import com.akatsuki.auth.dto.UserDto;
import com.akatsuki.auth.exception.BadRequestException;
import com.akatsuki.auth.model.User;
import com.akatsuki.auth.repository.UserRepository;
import com.akatsuki.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public GetUserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with id '%s' does not exist.", id)));
        return modelMapper.map(user, GetUserDto.class);
    }

    @Override
    public void createUser(UserDto userDto) {
        Optional<User> checkUser = userRepository.findByUsername(userDto.getUsername());

        if (checkUser.isPresent()) {
            throw new BadRequestException(String.format("Username '%s' is already in use.", userDto.getUsername()));
        }

        if (userDto.getPassword() != null && !userDto.getPassword().equals(userDto.getRepeatPassword())) {
            throw new BadRequestException("Passwords don't match.");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto) {
        Long id = updateUserDto.getId();
        User user = userRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with id '%s' does not exist.", id)));

        if (updateUserDto.getAddress() == null &&
                updateUserDto.getUsername() == null &&
                updateUserDto.getEmail() == null &&
                updateUserDto.getGivenName() == null &&
                updateUserDto.getLastName() == null &&
                updateUserDto.getPhoneNumber() == null &&
                updateUserDto.getPassword() == null
        ) {
            throw new BadRequestException("The input is not valid; You should pass at least one field to update in order to be successful.");
        }

        if (updateUserDto.getUsername() != null) {
//            TODO: Check if new username exist in db. Throw error if exist
            user.setUsername(updateUserDto.getUsername());
        }

        if (updateUserDto.getGivenName() != null) {
            user.setGivenName(updateUserDto.getGivenName());
        }
        if (updateUserDto.getLastName() != null) {
            user.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getEmail() != null) {
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getAddress() != null) {
            user.setAddress(updateUserDto.getAddress());
        }
        if (updateUserDto.getPassword() != null) {
            updateUserDto.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            user.setPassword(updateUserDto.getPassword());
        }
        if (updateUserDto.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with id '%s' does not exist.", id)));

//        TODO: First, send host id to reservations microservice then reservations should contact accommodation
//         service to gather info about their accommodation and then check do him have reservations in future, return that info here
//         If so, dont let him delete account. If he dont have reservations in future, let him, but trigger endpoint on
//         accommodation microservice to delete all his accommodations
//        TODO: If guest, then check have him already reserved something in future, if not, delete his account


        userRepository.delete(user);
    }

}
