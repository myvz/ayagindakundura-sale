package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.product.ProductDto;
import com.ayagindakundura.sale.domain.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping(value = "/search")
    public String findProductByBrand(@RequestParam(name = "brand") String brand, Model model) {
        List<ProductDto> products = productService.findProductByBrandName(brand);
        if (products.isEmpty()) {
            return "not-found";
        }
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping
    public String findProductById(@RequestParam(name = "id") Long id, Model model) {
        return productService.findProduct(id)
                .map(productDto -> {
                    model.addAttribute("product", productDto);
                    return "product";
                })
                .orElse("not-found");
    }
}
