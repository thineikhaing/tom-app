package com.nus.tom.service.impl;

import com.nus.tom.model.NotificationEvent;
import com.nus.tom.model.enums.EmailStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final EmailSender emailService;
    @Value("${spring.mail.username}")
    private String emailSender;

    /**
     * based on notification type, trigger the email content building strategy and send email
     * @param notificationEvent
     */
    public void invoke(NotificationEvent notificationEvent) {


        for (EmailStrategy emailStrategy : EmailStrategy.values()) {

            if (!emailStrategy.type.equalsIgnoreCase(notificationEvent.getType()))
                continue;

            Map<String, String> emailDetails = emailStrategy.getEmailBuilder().buildEmail(notificationEvent);
            if (Objects.isNull(emailDetails))
                return;

            emailService.sendEmail(notificationEvent.getSubject(), emailDetails, notificationEvent.getRecipients(), notificationEvent.getTemplate());

            return;

        }

    }

}
