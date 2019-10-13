package com.ayagindakundura.sale;

import com.ayagindakundura.sale.domain.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private ProductService productService;

    @Test
    public void should_reduce_stock() {
        saleService.purchase(1L, 10L);

        verify(productService).reduceStock(1L, 10L);
    }
}