package com.nus.tom;

import com.nus.tom.model.Employee;
import com.nus.tom.service.impl.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "classpath:application.yml")
public class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;
    @Test
    @DisplayName("test email")
    public void sendEmailTest(){
        emailService.sendEmail(Employee.builder().build());
    }
}
