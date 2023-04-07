package com.nus.tom;

import com.nus.tom.model.Employee;
import com.nus.tom.model.User;
import com.nus.tom.service.impl.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "classpath:application.yml")
public class EmailServiceTest {
    @InjectMocks
    private EmailTemplateService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(emailService,"emailSender","test");
        ReflectionTestUtils.setField(emailService,"activateUrl","test");
    }

    @Test
    @DisplayName("test email")
    public void sendEmailTest() {
        User user = new com.nus.tom.model.User();
        user.setId("1");

    }
}
