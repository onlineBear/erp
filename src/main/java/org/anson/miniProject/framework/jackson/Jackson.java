package org.anson.miniProject.framework.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Jackson {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Object value) throws JsonProcessingException {
        return Jackson.objectMapper.writeValueAsString(value);
    }

    public <T> T toObject(String content, Class<T> valueType) throws IOException {
        return Jackson.objectMapper.readValue(content, valueType);
    }
}
