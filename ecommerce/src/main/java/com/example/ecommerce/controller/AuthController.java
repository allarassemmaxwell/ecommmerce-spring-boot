package com.example.ecommerce.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class AuthController {
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This is the name of your custom login template
    }

    @PostMapping("/login")
    public String handleLogin() {
        return "redirect:/"; // Redirect to home page after login
    }

//    @GetMapping("/register")
//    public String registerPage(User user) {
//        return "register"; // Return your registration form template
//    }

//    @PostMapping("/register")
//    public String register(@Valid User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "register";
//        }
//
//        // Hash password
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setIsAdmin(false);  // Default user role
//        userRepository.save(user);
//
//        return "redirect:/login";
//    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "account/dashboard"; // Make sure you have a 'dashboard.html' view in templates
    }
}
