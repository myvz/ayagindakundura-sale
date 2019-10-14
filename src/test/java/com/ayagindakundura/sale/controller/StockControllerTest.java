package com.ayagindakundura.sale.controller;

import com.ayagindakundura.sale.domain.stock.StockChangedEventStreamService;
import com.ayagindakundura.sale.domain.stock.StockDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.JsonPathExpectationsHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @MockBean
    private StockChangedEventStreamService stockChangedEventStreamService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_stream_stock_changed_events() throws Exception {
        //Given
        StockDto stockDto = new StockDto(1L, 5);
        StockDto stockDto2 = new StockDto(1L, 4);

        Flux<StockDto> just = Flux.just(stockDto, stockDto2);
        when(stockChangedEventStreamService.getStockChangeStream(1L)).thenReturn(just);

        //When
        MvcResult mvcResult = this.mockMvc.perform(get("/stock/1"))
                .andExpect(request().asyncStarted())
                .andReturn();

        //Then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<String> stockDtos = Stream.of(contentAsString.replaceAll("\\n", "").split("data:"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        assertValue(stockDtos.get(0), "$.productId", 1);
        assertValue(stockDtos.get(0), "$.stockQuantity", 5);
        assertValue(stockDtos.get(1), "$.productId", 1);
        assertValue(stockDtos.get(1), "$.stockQuantity", 4);
    }

    private JsonPathExpectationsHelper jsonPathExpect(String s) {
        return new JsonPathExpectationsHelper(s);
    }

    public void assertValue(String value, String expression, int expectedValue) {
        jsonPathExpect(expression).assertValue(value, expectedValue);
    }

}