package com.nus.tom.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mya Pwint
 */
@Component
public class EventPublisher implements Publisher {

    private List<Listener> listeners = new ArrayList<>();


    @Override
    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void publish(Object event) {
        this.listeners.forEach(listener -> listener.listen(event));
    }
}
