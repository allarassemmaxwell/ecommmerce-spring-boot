package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String showProductPage(Model model) {
        // You can later add model attributes like a product list here
        return "products";  // This will render templates/products.html
    }
}
