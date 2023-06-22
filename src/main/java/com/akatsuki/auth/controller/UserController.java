package com.akatsuki.auth.controller;

import com.akatsuki.auth.dto.GetUserDto;
import com.akatsuki.auth.dto.UpdateUserDto;
import com.akatsuki.auth.dto.UserDto;
import com.akatsuki.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtDecoder jwtDecoder;

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/{id}")
//    public GetUserDto getUser(@PathVariable Long id) {
//        return userService.getUser(id);
//    }

    @GetMapping("/user-details")
    public GetUserDto getUserDetails(@RequestHeader("Authorization") final String token) {
        Long id = getIdFromToken(token);
        return userService.getUser(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@Valid @RequestBody UserDto userDto) {
        userService.createUser(userDto);
    }

    @PutMapping
    public void updateUser(@Valid @RequestBody UpdateUserDto userDto, @RequestHeader("Authorization") final String token) {
        Long id = getIdFromToken(token);
        userService.updateUser(userDto, id);
    }

    //    SAMO GOST
    @PutMapping("/cancellation")
    public void addCancellation(@RequestHeader("Authorization") final String token) {
        Long id = getIdFromToken(token);
        userService.addCancellation(id);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void deleteUser(@RequestHeader("Authorization") final String token) {
        Long id = getIdFromToken(token);
        userService.deleteUser(id);
    }

    private Long getIdFromToken(String token) {
        return (Long) jwtDecoder.decode(token).getClaims().get("id");
    }

}
