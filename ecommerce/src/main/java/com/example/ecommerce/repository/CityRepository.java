package com.example.ecommerce.repository;

import com.example.ecommerce.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
