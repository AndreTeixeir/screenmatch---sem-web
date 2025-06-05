package com.teixeira.screenmatch.service;

/**
 * Interface for data conversion operations.
 * Defines a generic method to convert a JSON string to an object of a specified class.
 */
public interface IDataConverter { // Renomeado para IDataConverter
    /**
     * Converts a JSON string to an object of the specified class.
     * @param <T> The type of the object to convert to.
     * @param jsonString The JSON string to be converted.
     * @param targetClass The Class object representing the type T.
     * @return An object of type T populated with data from the JSON string.
     */
    <T> T obtainData(String jsonString, Class<T> targetClass); // Renomeado parâmetros para jsonString e targetClass
}
