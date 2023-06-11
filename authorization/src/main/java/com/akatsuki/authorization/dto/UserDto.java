package com.akatsuki.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    @NotBlank
    private String role;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String givenName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String address;
    private String phoneNumber;

}