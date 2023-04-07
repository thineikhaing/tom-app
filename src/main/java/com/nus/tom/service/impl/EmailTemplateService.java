package com.nus.tom.service.impl;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateService {

    private final Configuration configuration;

    /**
     * @param content
     * @param template
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String getEmailContent(Map<String, String> content, String template) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        this.configuration.getTemplate(template).process(content, writer);
        return writer.getBuffer().toString();
    }

}
