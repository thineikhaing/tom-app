package com.nus.tom.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JsonHandler implements IJsonHandler {

    private final ObjectMapper objectMapper;

    @Override
    public <T> T fromJson(String json, Class<T> clazz) throws IOException {
        T dto = objectMapper.readValue(json, clazz);
        return dto;
    }
}
