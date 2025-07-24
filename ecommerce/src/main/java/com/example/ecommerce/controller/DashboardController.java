package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.CityRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.ProductRepository;

@Controller
public class DashboardController {
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public DashboardController(CityRepository cityRepository,
                               CategoryRepository categoryRepository,
                               UserRepository userRepository,
                               ProductRepository productRepository) {
        this.cityRepository = cityRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Model model) {
        long cityCount = cityRepository.count();
        long categoryCount = categoryRepository.count();
        long userCount = userRepository.count();
        long productCount = productRepository.count();

        model.addAttribute("cityCount", cityCount);
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("userCount", userCount);
        model.addAttribute("productCount", productCount);

        return "account/dashboard";
    }
}
