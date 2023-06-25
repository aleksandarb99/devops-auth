package com.akatsuki.auth.service.impl;

import com.akatsuki.auth.dto.GetUserDto;
import com.akatsuki.auth.dto.UpdateUserDto;
import com.akatsuki.auth.dto.UserDto;
import com.akatsuki.auth.enums.UserRole;
import com.akatsuki.auth.exception.BadRequestException;
import com.akatsuki.auth.feignclients.AccommodationFeignClient;
import com.akatsuki.auth.feignclients.ReservationFeignClient;
import com.akatsuki.auth.model.User;
import com.akatsuki.auth.repository.UserRepository;
import com.akatsuki.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final AccommodationFeignClient accommodationFeignClient;
    private final ReservationFeignClient reservationFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("That username is not found.");
        }
        return optionalUser.get();
    }

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
    public void addCancellation(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with id '%s' does not exist.", id)));
        user.setNumberOfCancelledReservations(user.getNumberOfCancelledReservations() + 1);
        userRepository.save(user);
    }

    @Override
    public void updateUser(UpdateUserDto updateUserDto, Long id) {
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
            Optional<User> checkUser = userRepository.findByUsername(updateUserDto.getUsername());
            if (checkUser.isPresent()) {
                if (checkUser.get().getId() == id) {
                    throw new BadRequestException(String.format("Username '%s' is already in use.", updateUserDto.getUsername()));
                }
            }

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
    public void deleteUser(Long id, String token) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new BadRequestException(String.format("User with id '%s' does not exist.", id)));

        if (user.getRole().equals(UserRole.HOST)) {
            boolean ifHostCanBeDeleted = reservationFeignClient.checkIfHostCanBeDeleted(token);
            if (!ifHostCanBeDeleted) {
                throw new BadRequestException("It is impossible to delete the account due to the existence of a reservation.");
            }

            userRepository.delete(user);
            accommodationFeignClient.deleteAccommodationsByHostId(token);
        } else {
            boolean ifGuestCanBeDeleted = reservationFeignClient.checkIfGuestCanBeDeleted(token);
            if (!ifGuestCanBeDeleted) {
                throw new BadRequestException("It is impossible to delete the account due to the existence of a reservation.");
            }

            userRepository.delete(user);
        }
    }

}
