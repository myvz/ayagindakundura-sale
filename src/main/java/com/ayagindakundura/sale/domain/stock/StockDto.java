package com.ayagindakundura.sale.domain.stock;

public class StockDto {

    private Long productId;

    private Long stockQuantity;

    public StockDto(Long productId, Long stockQuantity) {
        this.productId = productId;
        this.stockQuantity = stockQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

}
