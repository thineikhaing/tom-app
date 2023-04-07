package com.nus.tom.service.impl;

import com.nus.tom.model.NotificationEvent;
import com.nus.tom.service.EmailBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

import static com.nus.tom.util.TOMConstants.*;

/**
 * @author Mya Pwint
 */
@Slf4j
public class LeaveSubmisionEmailBuilder implements EmailBuilder {

    @Value("${user.urlToView}")
    private String urlToView;

    @Override
    public Map<String, String> buildEmail(NotificationEvent notificationEvent) {
        Map<String, String> emailDetails = new HashMap<>();
        emailDetails.put(MANAGER_NAME, notificationEvent.getEmployee().getDepartment().getDepartmentHead().getFullName());
        emailDetails.put(FULL_NAME, notificationEvent.getEmployee().getFullName());
        emailDetails.put(REASON, notificationEvent.getReason());

        return emailDetails;
    }
}
