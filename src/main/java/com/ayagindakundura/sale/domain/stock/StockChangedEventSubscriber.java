package com.ayagindakundura.sale.domain.stock;

@FunctionalInterface
public interface StockChangedEventSubscriber {
    void subscribe(StockChangedEvent stockChangedEvent);
}
