package com.nus.tom.service.impl;

import com.nus.tom.model.NotificationEvent;
import com.nus.tom.service.EmailBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.nus.tom.util.TOMConstants.FULL_NAME;
import static com.nus.tom.util.TOMConstants.LINK;

/**
 * @author Mya Pwint
 */


@Slf4j
@Component
public class RegistrationEmailBuilder implements EmailBuilder {
    @Override
    public Map<String, String> buildEmail(NotificationEvent notificationEvent) {

        Map<String, String> emailDetails = new HashMap<>();
        emailDetails.put(FULL_NAME, notificationEvent.getEmployee().getFullName());
        emailDetails.put(LINK, notificationEvent.getActivateUrl() + notificationEvent.getEmployee().getUser().getId());

        return emailDetails;
    }
}
