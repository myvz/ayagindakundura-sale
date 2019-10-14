package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.stock.OutOfStockException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(OutOfStockException.class)
    public String handOutOfStockException() {
        return "out-of-stock";
    }
}
