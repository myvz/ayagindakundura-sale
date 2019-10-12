package com.ayagindakundura.sale.repository;

import com.ayagindakundura.sale.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBrand_Name(String name);
}
