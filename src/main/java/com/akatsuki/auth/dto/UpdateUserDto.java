package com.akatsuki.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateUserDto {
    private String username;
    private String password;
    private String givenName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
}
