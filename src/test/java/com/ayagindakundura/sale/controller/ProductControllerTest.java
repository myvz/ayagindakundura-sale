package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.product.ProductDto;
import com.ayagindakundura.sale.domain.product.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_response_200_with_product_body_by_id() throws Exception {

        ProductDto dto = new ProductDto();
        when(productService.findProduct(1L)).thenReturn(Optional.of(dto));

        this.mockMvc.perform(get("/products?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product"))
                .andExpect(model().attribute("product", dto));
    }

    @Test
    public void should_response_not_found_page_when_product_not_found() throws Exception {
        this.mockMvc.perform(get("/products?id=1"))
                .andExpect(view().name("not-found"));
    }

    @Test
    public void should_list_products_by_name() throws Exception {
        String adidas = "adidas";
        List<ProductDto> products = Collections.singletonList(new ProductDto());
        when(productService.findProductByBrandName(adidas)).thenReturn(products);

        this.mockMvc.perform(get("/products/search?brand=adidas"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", products));
    }

    @Test
    public void should_response_not_found_page_when_product_search_is_empty() throws Exception {
        when(productService.findProductByBrandName(any())).thenReturn(Collections.emptyList());
        this.mockMvc.perform(get("/products/search?brand=adidas"))
                .andExpect(view().name("not-found"));
    }

}