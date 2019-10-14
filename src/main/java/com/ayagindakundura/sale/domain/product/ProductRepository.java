package com.ayagindakundura.sale.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByBrand_NameStartingWithIgnoreCase(String name);

    @Query("select p.stockQuantity from Product p where p.id=:id")
    Integer findStockQuantity(Long id);


    @Modifying
    @Query("update Product p set p.stockQuantity = p.stockQuantity - :quantity where p.id = :productId and (p.stockQuantity - :quantity) >= 0")
    int reduceStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
