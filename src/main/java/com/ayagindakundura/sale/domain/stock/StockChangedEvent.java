package com.ayagindakundura.sale.domain.stock;

public class StockChangedEvent {

    private Long productId;

    public StockChangedEvent(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
