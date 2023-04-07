package com.nus.tom.service.impl;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static com.nus.tom.util.TOMConstants.CONTENT_TYPE;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {
    private final JavaMailSender javaMailSender;

    private final EmailTemplateService emailTemplateService;
    @Value("${spring.mail.username}")
    private String emailSender;

    /**
     * send email
     * @param subject
     * @param emailDetails
     * @param recipients
     * @param templateName
     */
    void sendEmail(String subject, Map<String, String> emailDetails, Set<String> recipients, String templateName) {

        try {
            MimeMessage message = this.javaMailSender.createMimeMessage();
            message.setFrom(emailSender);
            InternetAddress[] addresses = new InternetAddress[recipients.size()];
            int i = 0;
            for (String recipient : recipients) {
                addresses[i++] = new InternetAddress(recipient);

            }
            message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            message.setSubject(subject);
            String emailContent = emailTemplateService.getEmailContent(emailDetails, templateName);
            message.setContent(emailContent, CONTENT_TYPE);

            log.info("Email receiver {} ", recipients);
            this.javaMailSender.send(message);

            log.info("Email sent {} ", recipients);
        } catch (Exception ex) {
            log.info("Email Sending Exception {}", ex);
        }


    }

}
