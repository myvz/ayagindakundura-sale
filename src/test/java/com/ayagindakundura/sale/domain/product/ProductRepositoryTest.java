package com.ayagindakundura.sale.domain.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void should_product_find_by_name() {

        Product adidas1 = createSampleProduct("Adidas", "Yellow", 10);
        Product adidas2 = createSampleProduct("Adidas", "Black", 10);
        Product nike = createSampleProduct("Nike", "Yellow", 10);

        List<Product> products = productRepository.findByBrand_NameStartingWithIgnoreCase("adid");

        assertThat(products).containsExactly(adidas1, adidas2);
    }

    @Test
    public void should_reduce_stock_when_available() {
        Product adidas1 = createSampleProduct("Adidas", "Yellow", 10);

        productRepository.reduceStock(adidas1.getId(), 5);
        testEntityManager.clear();
        testEntityManager.flush();

        Integer stockQuantity = productRepository.findStockQuantity(adidas1.getId());
        assertThat(stockQuantity).isEqualTo(5L);
    }

    @Test
    public void should_not_reduce_stock_when_out_of_stock() {
        Product adidas1 = createSampleProduct("Adidas", "Yellow", 2);

        productRepository.reduceStock(adidas1.getId(), 5);
        testEntityManager.clear();
        testEntityManager.flush();

        Integer stockQuantity = productRepository.findStockQuantity(adidas1.getId());
        assertThat(stockQuantity).isEqualTo(2L);
    }


    private Product createSampleProduct(String brandName, String color, Integer stockQuantity) {
        Brand brand = new Brand();
        brand.setName(brandName);
        brand = testEntityManager.persist(brand);

        Product product = new Product();
        product.setBrand(brand);
        product.setColor(color);
        product.setImageUrl("/yellow-" + brandName);
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(stockQuantity);
        product = testEntityManager.persist(product);
        testEntityManager.flush();
        testEntityManager.clear();
        return product;
    }

}