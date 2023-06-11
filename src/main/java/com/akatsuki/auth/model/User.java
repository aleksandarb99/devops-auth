package com.akatsuki.auth.model;

import com.akatsuki.auth.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(unique = true)
    private String username;
    private String password;
    private String givenName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
}
