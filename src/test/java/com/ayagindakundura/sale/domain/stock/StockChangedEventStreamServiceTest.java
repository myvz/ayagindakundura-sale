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
public class StockChangedEventStreamServiceTest {

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
    public void it_should_publish_stock_changed_event_and_subscribe_with_filtering() throws InterruptedException {
        //Given
        long productId = 1L;
        long productId2 = 2L;

        when(productRepository.findStockQuantity(productId)).thenReturn(5).thenReturn(4).thenReturn(3);
        when(productRepository.findStockQuantity(productId2)).thenReturn(10).thenReturn(3).thenReturn(3);

        Flux<StockDto> stockChangeStream = stockService.getStockChangeStream(productId);
        StepVerifier stepVerifier = StepVerifier.create(stockChangeStream)
                .expectNextMatches(stockDto -> stockDto.getProductId().equals(productId) && stockDto.getStockQuantity().equals(5))
                .expectNextMatches(stockDto -> stockDto.getProductId().equals(productId) && stockDto.getStockQuantity().equals(4))
                .expectNextMatches(stockDto -> stockDto.getProductId().equals(productId) && stockDto.getStockQuantity().equals(3))
                .thenCancel()
                .log()
                .verifyLater();

        Thread.sleep(100);

        //when
        publisher.publish(new StockChangedEvent(productId));
        publisher.publish(new StockChangedEvent(productId2));
        publisher.publish(new StockChangedEvent(productId));
        publisher.publish(new StockChangedEvent(productId2));
        publisher.publish(new StockChangedEvent(productId));
        publisher.publish(new StockChangedEvent(productId2));

        stepVerifier.verify();
    }

}