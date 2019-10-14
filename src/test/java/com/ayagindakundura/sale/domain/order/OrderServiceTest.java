package com.ayagindakundura.sale.domain.order;

import com.ayagindakundura.sale.domain.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private ProductService productService;

    @Test
    public void should_reduce_stock() {
        orderService.createOrder(1L, 10);

        verify(productService).reduceStock(1L, 10);
    }
}