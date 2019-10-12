package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.dto.ProductDto;
import com.ayagindakundura.sale.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

        this.mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void should_response_404_by_id_when_product_not_found() throws Exception {
        when(productService.findProduct(1L)).thenReturn(Optional.empty());

        this.mockMvc.perform(get("/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_list_products_by_name() {
        String adidas = "adidas";
        when(productService.findProductByBrandName(adidas)).thenReturn(Arrays.asList(new ProductDto()));
    }

}