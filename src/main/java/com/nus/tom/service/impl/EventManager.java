package com.nus.tom.service.impl;

import com.nus.tom.service.Listener;
import com.nus.tom.service.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Mya Pwint
 */
@Data
@Component
@AllArgsConstructor
public class EventManager {
    public Publisher publisher;

    public void bindListenerToPublisher(Listener listener) {
        this.publisher.addListener(listener);
    }

    public void unBindListenerToPublisher(Listener listener) {
        this.publisher.removeListener(listener);
    }
}
