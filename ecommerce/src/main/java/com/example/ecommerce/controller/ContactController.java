package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Contact;
import com.example.ecommerce.repository.ContactRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ContactController {
    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Handle GET request to display the contact form
    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());  // Bind a new Contact object to the form
        return "contact";  // Return the "contact" view
    }

    // Handle POST request when the form is submitted
    @PostMapping("/contact")
    public String submitContactForm(
            @Valid @ModelAttribute("contact") Contact contact,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "contact";  // If there are validation errors, return to the contact form
        }

        // Save the contact form data
        contactRepository.save(contact);

        // Add a success message and reset the form
        model.addAttribute("successMessage", "Thank you for contacting us, We will get back to you as soon as possible.");
        model.addAttribute("contact", new Contact());  // Reset the form
        return "contact";  // Return the contact page
    }

    @GetMapping("/dashboard/contacts")
    public String showContactsPage(Model model) {
        model.addAttribute("contacts", contactRepository.findAll());
        return "account/contacts";
    }
}

