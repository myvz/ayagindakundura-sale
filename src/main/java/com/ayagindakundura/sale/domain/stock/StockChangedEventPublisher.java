package com.ayagindakundura.sale.domain.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StockChangedEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockChangedEventPublisher.class);

    private Set<StockChangedEventSubscriber> subscribers = ConcurrentHashMap.newKeySet();

    public void subscribe(StockChangedEventSubscriber stockChangedEventSubscriber) {
        subscribers.add(stockChangedEventSubscriber);
    }

    public void unSubscribe(StockChangedEventSubscriber stockChangedEventSubscriber) {
        subscribers.remove(stockChangedEventSubscriber);
    }

    @Async
    public void publish(StockChangedEvent stockChangedEvent) {
        subscribers.forEach(subscriber -> subscriber.subscribe(stockChangedEvent));
    }
}
