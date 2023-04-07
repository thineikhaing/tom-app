package com.nus.tom.service;

import com.nus.tom.model.Employee;
import com.nus.tom.model.Leave;
import com.nus.tom.model.NotificationEvent;
import jakarta.mail.internet.MimeMessage;

import java.util.Map;

/**
 * @author Mya Pwint
 */
public interface EmailBuilder {
    Map<String, String> buildEmail( NotificationEvent notificationEvent);
}
