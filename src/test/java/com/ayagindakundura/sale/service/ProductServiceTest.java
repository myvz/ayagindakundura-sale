package com.ayagindakundura.sale.service;

import com.ayagindakundura.sale.converter.ProductConverter;
import com.ayagindakundura.sale.domain.Brand;
import com.ayagindakundura.sale.domain.Product;
import com.ayagindakundura.sale.dto.ProductDto;
import com.ayagindakundura.sale.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    private ProductConverter productConverter;

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