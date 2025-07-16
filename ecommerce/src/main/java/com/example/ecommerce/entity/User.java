package com.example.ecommerce.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "users") // renamed from "user" to avoid H2 conflict
@Data // ✅ Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // ✅ Default constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First Name is required")
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")
    private String email;

    private String phoneNumber;

    private String address;

    @NotEmpty(message = "Password is required")
    private String password;

    private Boolean isAdmin;
    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
