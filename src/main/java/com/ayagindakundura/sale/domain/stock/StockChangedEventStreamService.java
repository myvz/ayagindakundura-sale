package com.ayagindakundura.sale.domain.stock;

import com.ayagindakundura.sale.domain.product.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;

@Service
public class StockChangedEventStreamService {

    private Flux<StockDto> stockChangeStream;

    private StockChangedEventPublisher stockChangedEventPublisher;

    private ProductRepository productRepository;

    public StockChangedEventStreamService(StockChangedEventPublisher stockChangedEventPublisher, ProductRepository productRepository) {
        this.stockChangedEventPublisher = stockChangedEventPublisher;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void init() {
        this.stockChangeStream = Flux.create(this::mapAndRouteStockChangedEvent)
                .subscribeOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(2)));

    }

    public Flux<StockDto> getStockChangeStream(Long productId) {
        return stockChangeStream
                .filter(stockDto -> stockDto.getProductId().equals(productId));
    }

    private StockDto getStock(Long productId) {
        Long stockQuantity = productRepository.findStockQuantity(productId);
        return new StockDto(productId, stockQuantity);
    }

    private void mapAndRouteStockChangedEvent(FluxSink<StockDto> fluxSink) {
        stockChangedEventPublisher.subscribe(stockChangedEvent -> {
            StockDto stock = getStock(stockChangedEvent.getProductId());
            fluxSink.next(stock);
        });
    }
}
