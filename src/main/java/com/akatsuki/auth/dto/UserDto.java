package com.akatsuki.auth.dto;

import com.akatsuki.auth.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    @NotNull
    private UserRole role;
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