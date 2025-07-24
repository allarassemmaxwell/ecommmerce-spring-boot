package com.example.ecommerce.controller;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderController(ProductRepository productRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/place/{productId}")
    @Transactional
    public String placeOrder(@PathVariable Long productId,
                             @RequestParam(defaultValue = "1") Integer quantity,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {

        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to place an order.");
            return "redirect:/login";
        }

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null || quantity <= 0 || product.getQuantity() < quantity) {
            redirectAttributes.addFlashAttribute("error", "Invalid product or quantity.");
            return "redirect:/products";
        }

        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/login";
        }

        // Create Order
        Order order = new Order();
        order.setUser(user);
        List<OrderItem> items = new ArrayList<>();

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        items.add(item);

        order.setItems(items);
        order.setTotalAmount(item.getTotalPrice());

        product.setQuantity(product.getQuantity() - quantity);

        // Persist
        orderRepository.save(order);
        productRepository.save(product);

        redirectAttributes.addFlashAttribute("success", "Order placed successfully!");
        return "redirect:/products";
    }
}
