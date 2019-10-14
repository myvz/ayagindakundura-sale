package com.ayagindakundura.sale.domain.product;

import com.ayagindakundura.sale.domain.campaign.CampaignService;
import com.ayagindakundura.sale.domain.stock.OutOfStockException;
import com.ayagindakundura.sale.domain.stock.StockChangedEvent;
import com.ayagindakundura.sale.domain.stock.StockChangedEventPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

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

    @Captor
    private ArgumentCaptor<StockChangedEvent> stockChangedEventArgumentCaptor;

    @Test
    public void it_should_get_product_by_id() {
        long productId = 1L;
        Product sampleProduct = createSampleProduct("Adidas");

        when(productRepository.findById(productId)).thenReturn(Optional.of(sampleProduct));
        when(campaignService.getDiscountedPrice(sampleProduct)).thenReturn(BigDecimal.TEN);

        Optional<ProductDto> product = productService.findProduct(productId);

        assertThat(product.isPresent()).isTrue();
    }

    @Test
    public void it_should_get_product_by_name() {
        String adidas = "adidas";
        Product sampleProduct = createSampleProduct("adidas");

        when(productRepository.findByBrand_NameStartingWithIgnoreCase(adidas)).thenReturn(Collections.singletonList(sampleProduct));
        when(campaignService.getDiscountedPrice(sampleProduct)).thenReturn(BigDecimal.TEN);

        List<ProductDto> productByBrandName = productService.findProductByBrandName(adidas);
        ProductDto dto = productByBrandName.get(0);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBrand()).isEqualTo("adidas");
        assertThat(dto.getColor()).isEqualTo("Yellow");
        assertThat(dto.getImageUrl()).isEqualTo("/yellow-adidas");
        assertThat(dto.getPrice()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(dto.getStockQuantity()).isEqualTo(10L);
        assertThat(dto.getDiscountedPrice()).isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    public void it_should_reduce_stock_and_publish_stock_changed_event() {
        //Given
        long productId = 1L;
        int quantity = 10;
        when(productRepository.reduceStock(productId, quantity)).thenReturn(1);

        //When
        productService.reduceStock(productId, quantity);

        //Then
        verify(stockChangedEventPublisher).publish(stockChangedEventArgumentCaptor.capture());
        StockChangedEvent value = stockChangedEventArgumentCaptor.getValue();
        assertThat(value.getProductId()).isEqualTo(productId);
    }

    @Test
    public void it_should_throw_out_of_stock_exception_when_stock_is_not_available() {
        //Given
        long productId = 1L;
        int quantity = 10;
        when(productRepository.reduceStock(productId, quantity)).thenReturn(0);

        //When
        Throwable throwable = catchThrowable(() -> productService.reduceStock(productId, quantity));

        //Then
        assertThat(throwable).isInstanceOf(OutOfStockException.class);
        verifyZeroInteractions(stockChangedEventPublisher);
    }

    private Product createSampleProduct(String brandName) {
        Brand brand = new Brand();
        brand.setName(brandName);
        Product product = new Product();
        product.setId(1L);
        product.setBrand(brand);
        product.setColor("Yellow");
        product.setImageUrl("/yellow-adidas");
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(10);
        return product;
    }
}