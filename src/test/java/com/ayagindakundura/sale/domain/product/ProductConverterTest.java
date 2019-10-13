package com.ayagindakundura.sale.domain.product;


import com.ayagindakundura.sale.domain.product.Brand;
import com.ayagindakundura.sale.domain.product.Product;
import com.ayagindakundura.sale.domain.product.ProductConverter;
import com.ayagindakundura.sale.domain.product.ProductDto;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductConverterTest {

    ProductConverter productConverter = new ProductConverter();

    @Test
    public void it_should_convert_product_to_product_dto() {
        Product product = createSampleProduct();

        BigDecimal discountedPrice = BigDecimal.valueOf(8);
        ProductDto dto = productConverter.convert(product, discountedPrice);
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBrand()).isEqualTo("Adidas");
        assertThat(dto.getColor()).isEqualTo("Yellow");
        assertThat(dto.getImageUrl()).isEqualTo("/yellow-adidas");
        assertThat(dto.getPrice()).isEqualByComparingTo(BigDecimal.TEN);
        assertThat(dto.getStockQuantity()).isEqualTo(10L);
        assertThat(dto.getDiscountedPrice()).isEqualByComparingTo(discountedPrice);

    }

    private Product createSampleProduct() {
        Brand brand = new Brand();
        brand.setName("Adidas");
        Product product = new Product();
        product.setId(1L);
        product.setBrand(brand);
        product.setColor("Yellow");
        product.setImageUrl("/yellow-adidas");
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(10L);
        return product;
    }

}