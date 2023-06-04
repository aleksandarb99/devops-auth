package com.akatsuki.authorization.controller;

import com.akatsuki.authorization.dto.GetUserDto;
import com.akatsuki.authorization.dto.UpdateUserDto;
import com.akatsuki.authorization.dto.UserDto;
import com.akatsuki.authorization.service.AuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/authorization")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@Valid @RequestBody UserDto userDto) {
        authorizationService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public void updateUser(@Valid @RequestBody UpdateUserDto userDto) {
        authorizationService.updateUser(userDto);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        authorizationService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetUserDto getUser(@PathVariable Long id) {
        return authorizationService.getUser(id);
    }
}
