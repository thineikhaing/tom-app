package com.nus.tom.util;

import java.io.IOException;

public interface IJsonHandler {
    public static final String[] DATE_FORMAT = new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSZ"};

    public <T> T fromJson(String json,Class<T> clazz) throws IOException;
}
