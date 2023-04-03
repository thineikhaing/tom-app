package com.nus.tom.service.impl;

import com.nus.tom.model.Employee;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static com.nus.tom.util.TOMConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${user.activateUrl}")
    private String activateUrl;

    private final Configuration configuration;

    /**
     * to send email
     * need design pattern - strategy to set email content and template
     *
     * @param employee
     */
    public void sendEmail(Employee employee) {

        Map<String, String> emailDetails = new HashMap<>();
        emailDetails.put(FULL_NAME, employee.getFullName());
        emailDetails.put(STATUS, employee.getFullName());
        emailDetails.put(LINK, activateUrl + employee.getUser().getId());

        try {
            String subject = REGISTRATION;
            MimeMessage message = this.javaMailSender.createMimeMessage();
            message.setFrom(emailSender);
            message.setRecipients(MimeMessage.RecipientType.TO, employee.getUser().getEmail());
            message.setSubject(subject);
            message.setContent(getEmailContent(emailDetails, EMPLOYEE_FTL), CONTENT_TYPE);
            this.javaMailSender.send(message);
            log.info("Email receiver {} ", employee.getUser().getEmail());

        } catch (Exception ex) {
            log.error("Mail Exception. ", ex);
        }

    }

    /**
     * @param content
     * @param template
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    private String getEmailContent(Map<String, String> content, String template) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        this.configuration.getTemplate(template).process(content, writer);
        return writer.getBuffer().toString();
    }

}
