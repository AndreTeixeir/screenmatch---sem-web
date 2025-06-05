package com.teixeira.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

/**
 * Service class responsible for consuming REST APIs.
 * Annotated with @Service so Spring can manage this class as a component.
 */
@Service
public class ApiConsumer {

    // Ideally, HttpClient should be a singleton or managed by Spring for reuse.
    // For this simple example, instantiating here is acceptable, but in production, consider:
    // private final HttpClient httpClient = HttpClient.newHttpClient();
    // or injecting it if complex configurations are needed.

    /**
     * Performs an HTTP GET request to the provided address and returns the response body as a String.
     *
     * @param address The complete URL of the API to be consumed.
     * @return The HTTP response body as a String (usually JSON or XML).
     * @throws RuntimeException If an I/O error occurs or the request is interrupted.
     */
    public String obtainData(String address) { // Renomeado para address
        HttpClient httpClient = HttpClient.newHttpClient(); // Renomeado para httpClient

        HttpRequest httpRequest = HttpRequest.newBuilder() // Renomeado para httpRequest
                .uri(URI.create(address)) // Converts the address String into a URI object
                .build(); // Builds the request

        HttpResponse<String> httpResponse; // Renomeado para httpResponse

        try {
            // Sends the request and obtains the response, expecting the body as a String
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // Throws a RuntimeException for I/O errors (network, read/write)
            System.err.println("I/O error consuming the API: " + e.getMessage());
            throw new RuntimeException("I/O error obtaining data from the API.", e);
        } catch (InterruptedException e) {
            // Throws a RuntimeException if the thread is interrupted while waiting for the response
            System.err.println("API request was interrupted: " + e.getMessage());
            // Restores the thread's interrupted status
            Thread.currentThread().interrupt();
            throw new RuntimeException("API request interrupted.", e);
        }

        // Returns the response body
        return httpResponse.body();
    }
}
