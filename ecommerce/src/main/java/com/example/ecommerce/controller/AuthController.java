package com.example.ecommerce.controller;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User()); // bind User object for register form
        return "login"; // login.html contains both login and register
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model) {
        if (result.hasErrors()) {
            return "login"; // return to the same login/register page
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user", "Email is already registered.");
            return "login";
        }
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("confirmPasswordError", "Passwords do not match.");
        }

        if (result.hasErrors() || model.containsAttribute("confirmPasswordError")) {
            return "login";
        }

        // Set default properties
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsAdmin(false);
        user.setIsActive(true);

        userRepository.save(user);
//        model.addAttribute("registerSuccess", "Registration successful. You can now log in.");
        model.addAttribute("user", new User()); // Reset form
        model.addAttribute("registerSuccess", "Registration successful. You can now log in.");
        return "login";


//        return "login"; // Show same page with success message
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "account/dashboard";
    }
}
