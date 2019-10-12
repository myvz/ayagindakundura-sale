package com.ayagindakundura.sale.repository;

import com.ayagindakundura.sale.domain.Brand;
import com.ayagindakundura.sale.domain.Product;
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

        Product adidas1 = createSampleProduct("Adidas", "Yellow");
        Product adidas2 = createSampleProduct("Adidas", "Black");
        Product nike = createSampleProduct("Nike", "Yellow");

        List<Product> products = productRepository.findByBrand_Name("Adidas");

        assertThat(products).containsExactly(adidas1, adidas2);
    }


    private Product createSampleProduct(String brandName, String color) {
        Brand brand = new Brand();
        brand.setName(brandName);
        brand = testEntityManager.persist(brand);

        Product product = new Product();
        product.setBrand(brand);
        product.setColor(color);
        product.setImageUrl("/yellow-" + brandName);
        product.setPrice(BigDecimal.TEN);
        product.setStockQuantity(10L);
        product = testEntityManager.persist(product);
        testEntityManager.flush();
        testEntityManager.clear();
        return product;
    }

}