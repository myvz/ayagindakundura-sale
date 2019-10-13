package com.ayagindakundura.sale;

import com.ayagindakundura.sale.domain.product.ProductService;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    private ProductService productService;

    public SaleService(ProductService productService) {
        this.productService = productService;
    }

    public void purchase(Long productId, Long quantity) {
        productService.reduceStock(productId, quantity);
    }
}
