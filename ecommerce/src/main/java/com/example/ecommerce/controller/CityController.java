package com.example.ecommerce.controller;

import com.example.ecommerce.entity.City;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ecommerce.repository.CityRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CityController {
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/dashboard/cities")
    public String showCitiesPage(Model model) {
        model.addAttribute("cities", cityRepository.findAll());
        return "account/cities";
    }

    @GetMapping("/dashboard/cities/add")
    public String showAddCityForm(Model model) {
        model.addAttribute("city", new City());
        return "account/city_add";
    }

    @PostMapping("/dashboard/cities/add")
    public String addCity(@ModelAttribute @Valid City city,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "There was an error in your submission.");
            return "account/city_add";
        }

        // Save the product to the repository
        cityRepository.save(city);

        // Add a success message as a flash attribute
        redirectAttributes.addFlashAttribute("success", "City added successfully!");

        // Redirect to the products dashboard
        return "redirect:/dashboard/cities";
    }
}
