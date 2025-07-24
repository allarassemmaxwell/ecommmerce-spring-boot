package com.example.ecommerce.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data // ✅ Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // ✅ Default constructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String imageUrl; // ✅ URL or path to image

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(0)
    private Integer quantity;

    private String size; // e.g., "Small", "Medium", "Large" or even "120x60cm"
    private String color;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double discount; // In percent: 0-100

    @Column(length = 1000)  // You can set this to a higher value, or use TEXT type in DB
    private String description;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        isActive = true; // Optional: set default
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
