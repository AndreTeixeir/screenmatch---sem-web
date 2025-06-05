package com.teixeira.screenmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teixeira.screenmatch.model.SeriesData; // Renomeado para SeriesData
import com.teixeira.screenmatch.service.ApiConsumer; // Renomeado para ApiConsumer
import com.teixeira.screenmatch.service.IDataConverter; // Renomeado para IDataConverter
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Spring Boot application.
 * Implements CommandLineRunner to execute specific code after the Spring context initialization.
 */
@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	// Injects the ApiConsumer dependency.
	@Autowired
	private ApiConsumer apiConsumer; // Renomeado para apiConsumer

	// Injects the IDataConverter dependency. Spring will find a concrete implementation (e.g., DataConverter)
	@Autowired
	private IDataConverter converter; // Renomeado para converter

	public static void main(String[] args) {
		// Standard entry point for the Java application. Starts the Spring Boot application.
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	/**
	 * This method is automatically executed by Spring Boot after the application context is initialized.
	 * Ideal for initialization logic, quick tests, or command-line argument processing.
	 * @param args Command-line arguments passed to the application.
	 * @throws Exception if an error occurs during execution.
	 */
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Screenmatch Application started. Executing CommandLineRunner...");

		// Using the API key and movie title you provided for testing
		String movieTitle = "Gilmore Girls"; // Renomeado para movieTitle
		// WARNING: In a real application, the API key should not be hardcoded.
		// Use application.properties or environment variables for security.
		String apiKey = "6585022c"; // Already in English

		// Building the URL for the request. Spaces are replaced with '+' in the query string.
		String apiUrl = "https://www.omdbapi.com/?t=" + movieTitle.replace(" ", "+") + "&apikey=" + apiKey; // Renomeado para apiUrl

		System.out.println("Consuming API at: " + apiUrl);

		try {
			// Calls the obtainData method of the injected ApiConsumer instance
			String jsonResponse = apiConsumer.obtainData(apiUrl); // Renomeado para jsonResponse

			System.out.println("\nAPI JSON Response:");
			System.out.println(jsonResponse);

			// Now, parse the JSON to the Java object using the injected converter
			// The obtainData method of the converter is now called with the JSON response
			SeriesData seriesData = converter.obtainData(jsonResponse, SeriesData.class); // Renomeado para seriesData

			System.out.println("\nSeries Data (Java Object):");
			System.out.println("Title: " + seriesData.title()); // Acessando o getter do record
			System.out.println("Total Seasons: " + seriesData.totalSeasons()); // Acessando o getter do record
			System.out.println("IMDb Rating: " + seriesData.imdbRating()); // Acessando o getter do record

		} catch (RuntimeException e) {
			System.err.println("An error occurred while consuming or processing the API: " + e.getMessage());
			// Stack trace details for debugging
			e.printStackTrace(); // Good to keep for debugging in development
		}

		System.out.println("\nCommandLineRunner execution finished.");
	}
}
