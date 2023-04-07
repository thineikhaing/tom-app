package com.nus.tom.model;

import com.nus.tom.service.EmailBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Mya Pwint
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class NotificationEvent implements Serializable {

    private Employee employee;

    private String status;

    private String reason;

    private Set<String> recipients;

    private String template;

    private String subject;

    private String type;

    private String activateUrl;

}
