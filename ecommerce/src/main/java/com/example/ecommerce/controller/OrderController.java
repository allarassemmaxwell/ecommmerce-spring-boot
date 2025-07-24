package com.example.ecommerce.controller;

import com.example.ecommerce.repository.CityRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ecommerce.repository.CityRepository;

public class OrderController {
    private final CityRepository cityRepository;

    public OrderController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping("/dashboard/orders")
    public String showMyOrdersPage(Model model) {
        model.addAttribute("cities", cityRepository.findAll());
        return "account/orders";
    }
}
