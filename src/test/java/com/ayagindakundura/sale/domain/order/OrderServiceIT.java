package com.ayagindakundura.sale.domain.order;

import com.ayagindakundura.sale.domain.product.Product;
import com.ayagindakundura.sale.domain.stock.OutOfStockException;
import com.ayagindakundura.sale.domain.stock.StockChangedEventStreamService;
import com.ayagindakundura.sale.domain.stock.StockDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {"spring.datasource.initialization-mode=embedded"})
public class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StockChangedEventStreamService stockChangedEventStreamService;

    @Test
    public void should_update_stock_and_publish_event() {
        //Given
        long productId = 1L;

        Flux<StockDto> stockChangeStream = stockChangedEventStreamService.getStockChangeStream(productId);

        StepVerifier stepVerifier = StepVerifier.create(stockChangeStream)
                .expectNextMatches(stockDto -> stockDto.getProductId().equals(productId))
                .expectNextMatches(stockDto -> stockDto.getProductId().equals(productId))
                .expectNextCount(0)
                .thenCancel()
                .log()
                .verifyLater();

        //when
        orderService.createOrder(productId, 10);
        orderService.createOrder(productId, 10);
        Throwable throwable = catchThrowable(() -> orderService.createOrder(productId, 90));

        //Then
        assertThat(throwable).isInstanceOf(OutOfStockException.class);
        Product product = entityManager.find(Product.class, productId);
        assertThat(product.getStockQuantity()).isEqualTo(80);
        stepVerifier.verify();
    }
}