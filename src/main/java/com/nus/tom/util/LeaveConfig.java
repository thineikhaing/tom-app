package com.nus.tom.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "leave")
public class LeaveConfig {
    private Map<String, Integer> mapping;
}
