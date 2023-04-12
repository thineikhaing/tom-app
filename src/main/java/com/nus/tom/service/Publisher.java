package com.nus.tom.service;

/**
 * @author Mya Pwint
 */
public interface Publisher {
    public void addListener(Listener listener);

    public void removeListener(Listener listener);

    public void publish(Object subject);

}
