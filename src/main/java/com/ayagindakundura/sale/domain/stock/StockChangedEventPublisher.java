package com.ayagindakundura.sale.domain.stock;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StockChangedEventPublisher {

    private Set<StockChangedEventSubscriber> subscribers = ConcurrentHashMap.newKeySet();

    public void subscribe(StockChangedEventSubscriber stockChangedEventSubscriber) {
        subscribers.add(stockChangedEventSubscriber);
    }

    @Async
    public void publish(StockChangedEvent stockChangedEvent) {
        subscribers.forEach(subscriber -> subscriber.subscribe(stockChangedEvent));
    }
}
