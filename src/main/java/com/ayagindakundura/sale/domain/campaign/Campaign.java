package com.ayagindakundura.sale.domain.campaign;

import com.ayagindakundura.sale.domain.product.Product;

import java.math.BigDecimal;

@FunctionalInterface
public interface Campaign {

    BigDecimal applyDiscount(Product product);
}
