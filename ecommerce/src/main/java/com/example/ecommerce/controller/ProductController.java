package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/products")
    public String showProductPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products";  // This will render templates/products.html
    }

    @GetMapping("/dashboard/products")
    public String showDashboardProductsPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "account/products";
    }

    @GetMapping("/dashboard/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "account/product_add";
    }

    @PostMapping("/dashboard/products/add")
    public String addProduct(@ModelAttribute @Valid Product product,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "There was an error in your submission.");
            return "account/product_add";
        }

        // Save the product to the repository
        productRepository.save(product);

        // Add a success message as a flash attribute
        redirectAttributes.addFlashAttribute("success", "Product added successfully!");

        // Redirect to the products dashboard
        return "redirect:/dashboard/products";
    }

}
