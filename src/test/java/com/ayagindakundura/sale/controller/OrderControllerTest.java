package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.order.OrderService;
import com.ayagindakundura.sale.domain.stock.OutOfStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_create_sale_and_response_with_sale_page() throws Exception {
        this.mockMvc.perform(post("/order")
                .param("productId", "1")
                .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("sold"))
                .andExpect(model().attribute("productId",1L));

        verify(orderService).createOrder(1L, 10);
    }

    @Test
    public void should_response_with_out_of_stock_page_when_stocks_is_not_available() throws Exception {

        doThrow(new OutOfStockException()).when(orderService).createOrder(1L, 10);

        this.mockMvc.perform(post("/order")
                .param("productId", "1")
                .param("quantity", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("out-of-stock"));
    }

}