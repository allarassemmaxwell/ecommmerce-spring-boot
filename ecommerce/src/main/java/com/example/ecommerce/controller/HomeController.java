package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // ← you forgot this
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact");
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // this maps to login.html in /templates
    }
}
