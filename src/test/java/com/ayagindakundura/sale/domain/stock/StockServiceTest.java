package com.ayagindakundura.sale.domain.stock;

import com.ayagindakundura.sale.domain.product.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    @InjectMocks
    private StockChangedEventStreamService stockService;

    @Spy
    private StockChangedEventPublisher publisher = new StockChangedEventPublisher();

    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {
        stockService.init();
    }

    @Test
    public void it_should_publish_stock_changed_event() {
        //Given
        long productId = 1L;
        long productId2 = 2L;

        when(productRepository.findStockQuantity(productId)).thenReturn(5L, 4L, 3L);
        when(productRepository.findStockQuantity(productId2)).thenReturn(10L, 4L);
        Flux<StockDto> stockChangeStream = stockService.getStockChangeStream(productId);

        //when
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publisher.publish(new StockChangedEvent(productId));
            publisher.publish(new StockChangedEvent(productId2));
            publisher.publish(new StockChangedEvent(productId));
            publisher.publish(new StockChangedEvent(productId2));
            publisher.publish(new StockChangedEvent(productId));
        }).start();

        //Then
        StepVerifier.create(stockChangeStream)
                .expectNextMatches(stockDto -> stockDto.getStockQuantity().equals(5L) && stockDto.getProductId().equals(productId))
                .expectNextMatches(stockDto -> stockDto.getStockQuantity().equals(4L) && stockDto.getProductId().equals(productId))
                .expectNextMatches(stockDto -> stockDto.getStockQuantity().equals(3L) && stockDto.getProductId().equals(productId))
                .thenCancel()
                .verify();
    }

}