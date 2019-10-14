package com.ayagindakundura.sale.domain.stock;

public class StockDto {

    private Long productId;

    private Integer stockQuantity;

    public StockDto(Long productId, Integer stockQuantity) {
        this.productId = productId;
        this.stockQuantity = stockQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

}
