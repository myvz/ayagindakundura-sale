package com.ayagindakundura.sale.domain.order;

import com.ayagindakundura.sale.domain.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private ProductService productService;

    public OrderService(ProductService productService) {
        this.productService = productService;
    }

    public void createOrder(Long productId, Integer quantity) {
        productService.reduceStock(productId, quantity);
    }
}
