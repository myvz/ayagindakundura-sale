package com.ayagindakundura.sale.converter;

import com.ayagindakundura.sale.domain.Product;
import com.ayagindakundura.sale.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductConverter {

    public ProductDto convert(Product product, BigDecimal discountedPrice) {
        ProductDto dto = new ProductDto();
        dto.setBrand(product.getBrand().getName());
        dto.setColor(product.getColor());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setDiscountedPrice(discountedPrice);
        return dto;
    }
}
