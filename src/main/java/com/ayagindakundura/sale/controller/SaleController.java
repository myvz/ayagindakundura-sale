package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sale")
public class SaleController {

    private SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;

    }

    @PostMapping
    public String purchase(@RequestParam("productId") Long productId, @RequestParam("quantity") Long quantity) {
        saleService.purchase(productId, quantity);
        return "sold";
    }
}
