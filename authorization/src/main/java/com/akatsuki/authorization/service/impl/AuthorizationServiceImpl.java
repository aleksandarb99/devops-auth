package com.akatsuki.authorization.service.impl;

import java.util.Optional;

import com.akatsuki.authorization.dto.GetUserDto;
import com.akatsuki.authorization.dto.UpdateUserDto;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.akatsuki.authorization.dto.UserDto;
import com.akatsuki.authorization.model.User;
import com.akatsuki.authorization.exception.BadRequestException;
import com.akatsuki.authorization.repository.UserRepository;
import com.akatsuki.authorization.service.AuthorizationService;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createUser(UserDto userDto) {
        Optional<User> checkUser = userRepository.findByUsername(userDto.getUsername());

        if (checkUser.isPresent()) {
            throw new BadRequestException(String.format("Username '%s' is already in use.", userDto.getUsername()));
        }

        if(userDto.getPassword()!= null && !userDto.getPassword().equals(userDto.getRepeatPassword())){
            throw new BadRequestException(String.format("Passwords don't match."));
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = modelMapper.map(userDto, User.class);
        userRepository.save(user);
    }

    private User checkUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("Accommodation with id '%s' does not exist.", id)));
        return user;
    }
    @Override
    public void deleteUser(Long id) {
        User user = checkUserById(id);
        userRepository.delete(user);
    }

    @Override
    public GetUserDto getUser(Long id) {
        User user = checkUserById(id);

        GetUserDto userDto = modelMapper.map(user, GetUserDto.class);
        return userDto;
    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto) {
        User user = checkUserById(updateUserDto.getId());

        if (updateUserDto.getUsername() != null){
            user.setUsername(updateUserDto.getUsername());
        }

        if (updateUserDto.getGivenName() != null){
            user.setGivenName(updateUserDto.getGivenName());
        }
        if (updateUserDto.getLastName() != null){
            user.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getEmail() != null){
            user.setEmail(updateUserDto.getEmail());
        }
        if (updateUserDto.getAddress() != null){
            user.setAddress(updateUserDto.getAddress());
        }
        if (updateUserDto.getPassword() != null){
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            updateUserDto.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            user.setPassword(updateUserDto.getPassword());
        }
        if (updateUserDto.getPhoneNumber() != null){
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        }

        userRepository.save(user);
    }

}
