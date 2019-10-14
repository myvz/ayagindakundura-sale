package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }

    @PostMapping
    public String purchase(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity, Model model) {
        orderService.createOrder(productId, quantity);
        model.addAttribute("productId", productId);
        return "sold";
    }
}
