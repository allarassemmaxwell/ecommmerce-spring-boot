package com.example.ecommerce.controller;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public DashboardController(CityRepository cityRepository,
                               CategoryRepository categoryRepository,
                               UserRepository userRepository,
                               ProductRepository productRepository,
                               OrderRepository orderRepository) {
        this.cityRepository = cityRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboardPage(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        model.addAttribute("cityCount", cityRepository.count());
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("orderCount", orderRepository.count());

        model.addAttribute("allOrders", orderRepository.findAll());

        if (userDetails != null) {
            userRepository.findByEmail(userDetails.getUsername()).ifPresent(user ->
                    model.addAttribute("myOrders", orderRepository.findByUser(user)));
        }

        return "account/dashboard";
    }
}