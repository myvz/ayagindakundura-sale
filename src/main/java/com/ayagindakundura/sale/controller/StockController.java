package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.stock.StockChangedEventStreamService;
import com.ayagindakundura.sale.domain.stock.StockDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/stock")
public class StockController {

    private StockChangedEventStreamService stockService;

    public StockController(StockChangedEventStreamService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<StockDto> get(@PathVariable(name = "productId") Long productId) {
        return stockService.getStockChangeStream(productId);
    }
}
