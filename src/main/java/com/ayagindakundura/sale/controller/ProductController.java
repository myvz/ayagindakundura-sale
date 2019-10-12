package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.dto.ProductDto;
import com.ayagindakundura.sale.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(name = "id") Long id) {
        return productService.findProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<ProductDto> getProduct(@RequestParam(name = "brand") String brand) {
        return productService.findProductByBrandName(brand);
    }
}
