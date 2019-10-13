package com.ayagindakundura.sale.domain.product;

import com.ayagindakundura.sale.domain.campaign.CampaignService;
import com.ayagindakundura.sale.domain.stock.StockChangedEvent;
import com.ayagindakundura.sale.domain.stock.StockChangedEventPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CampaignService campaignService;

    @Mock
    private StockChangedEventPublisher stockChangedEventPublisher;

    @Spy
    private ProductConverter productConverter;

    @Captor
    private ArgumentCaptor<StockChangedEvent> stockChangedEventArgumentCaptor;

    @Test
    public void it_should_get_product_by_id() {
        long productId = 1L;
        Product sampleProduct = createSampleProduct();

        when(productRepository.findById(productId)).thenReturn(Optional.of(sampleProduct));
        when(campaignService.getDiscountedPrice(sampleProduct)).thenReturn(BigDecimal.TEN);

        Optional<ProductDto> product = productService.findProduct(productId);

        assertThat(product.isPresent()).isTrue();
        verify(productConverter).convert(sampleProduct, BigDecimal.TEN);
    }

    @Test
    public void it_should_reduce_stock_and_publish_stock_changed_event() {

        long productId = 1L;
        long quantity = 10L;
        productService.reduceStock(productId, quantity);

        verify(productRepository).reduceStock(productId, quantity);
        verify(stockChangedEventPublisher).publish(stockChangedEventArgumentCaptor.capture());
        StockChangedEvent value = stockChangedEventArgumentCaptor.getValue();
        assertThat(value.getProductId()).isEqualTo(productId);
    }

    private Product createSampleProduct() {
        Brand brand = new Brand();
        brand.setName("Adidas");
        Product product = new Product();
        product.setBrand(brand);
        product.setColor("Yellow");
        product.setImageUrl("/yellow-adidas");
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(10L);
        return product;
    }
}