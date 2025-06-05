package com.teixeira.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Class responsible for converting JSON data to Java objects.
 * Implements IDataConverter.
 * Annotated with @Component so Spring can manage and inject this class where needed.
 */
@Component
public class DataConverter implements IDataConverter{ // Renomeado para DataConverter
    private ObjectMapper objectMapper = new ObjectMapper(); // Renomeado para objectMapper

    @Override
    public <T> T obtainData(String jsonString, Class<T> targetClass) { // Renomeado parâmetros para jsonString e targetClass
        try {
            return objectMapper.readValue(jsonString, targetClass);
        } catch (JsonProcessingException e) {
            // It's important to log the error or throw a more specific exception if necessary.
            System.err.println("Error processing JSON: " + e.getMessage());
            throw new RuntimeException("Failed to convert JSON data.", e);
        }
    }
}
