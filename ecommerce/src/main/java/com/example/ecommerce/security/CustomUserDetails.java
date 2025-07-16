package com.example.ecommerce.security;
import com.example.ecommerce.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Required by UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // You can return roles here if needed. For now, return empty list
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Already encoded in DB
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // Login using email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // you can customize this
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // or user.isActive() if you want to lock inactive users
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(user.getIsActive()); // Check activation
    }

    public User getUser() {
        return user;
    }
}

