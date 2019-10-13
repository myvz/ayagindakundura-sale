package com.ayagindakundura.sale.domain.product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductConverter {

    public ProductDto convert(Product product, BigDecimal discountedPrice) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setBrand(product.getBrand().getName());
        dto.setColor(product.getColor());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setDiscountedPrice(discountedPrice);
        return dto;
    }
}
