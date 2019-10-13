package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.stock.StockChangedEventStreamService;
import com.ayagindakundura.sale.domain.stock.StockDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @MockBean
    private StockChangedEventStreamService stockChangedEventStreamService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_stream_stock_changed_events() throws Exception {
        StockDto stockDto = new StockDto(1L, 5L);

        Flux<StockDto> just = Flux.just(stockDto);
        when(stockChangedEventStreamService.getStockChangeStream(1L)).thenReturn(just);


        MvcResult mvcResult = this.mockMvc.perform(get("/stock/1"))
                .andExpect(request().asyncStarted())
                .andReturn();


        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_STREAM_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.stockQuantity").value(5L));
    }

}