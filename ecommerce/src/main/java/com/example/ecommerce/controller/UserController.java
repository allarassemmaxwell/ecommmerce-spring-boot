package com.example.ecommerce.controller;

import com.example.ecommerce.entity.City;
import com.example.ecommerce.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.CityRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;

    // Inject both UserRepository and CityRepository into the controller
    public UserController(UserRepository userRepository, CityRepository cityRepository) {
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping("/dashboard/users")
    public String showUsersPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "account/users";
    }

    @GetMapping("/dashboard/users/add")
    public String showAddUserForm(Model model) {
        // Create an empty User object to be populated in the form
        model.addAttribute("user", new User());

        // Fetch all cities and add them to the model for use in the dropdown
        List<City> cities = cityRepository.findAll();
        model.addAttribute("cities", cities);

        return "account/user_add"; // Your HTML form page for adding a user
    }

    @PostMapping("/dashboard/users/add")
    public String addUser(@ModelAttribute @Valid User user,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form page with errors
            redirectAttributes.addFlashAttribute("error", "There was an error in your submission.");
            return "account/user_add";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "error.user", "Email is already registered.");
            return "account/user_add";
        }

        // Save the user to the repository
        userRepository.save(user);

        // Add a success message to be displayed on the next page
        redirectAttributes.addFlashAttribute("success", "User added successfully!");

        // Redirect to the users dashboard after successful addition
        return "redirect:/dashboard/users";
    }
}
