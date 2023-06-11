package com.akatsuki.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserDto {
    @NotNull
    private Long id;
    private String username;
    private String password;
    private String givenName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
}
