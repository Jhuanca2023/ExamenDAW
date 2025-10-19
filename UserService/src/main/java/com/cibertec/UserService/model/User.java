package com.cibertec.UserService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String provider;
    private String providerId;
    private String role;

    @Enumerated(EnumType.STRING)
    private AuthProvider providerType;

    public User() {
        this.role = "USER";
        this.providerType = AuthProvider.local;
    }

    // Constructor con parámetros básicos
    public User(String name, String email, String password) {
        this();
        this.name = name;
        this.email = email;
        this.password = password;
    }
}