package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.City;
import com.example.ecommerce.repository.CityRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/dashboard/categories")
    public String showCategoriesPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "account/categories";
    }


    @GetMapping("/dashboard/categories/add")
    public String showAddCityForm(Model model) {
        model.addAttribute("category", new Category());
        return "account/category_add";
    }

    @PostMapping("/dashboard/categories/add")
    public String addCity(@ModelAttribute @Valid Category category,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "There was an error in your submission.");
            return "account/category_add";
        }

        // Save the product to the repository
        categoryRepository.save(category);

        // Add a success message as a flash attribute
        redirectAttributes.addFlashAttribute("success", "Category added successfully!");

        // Redirect to the products dashboard
        return "redirect:/dashboard/categories";
    }
}
