package com.teixeira.screenmatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teixeira.screenmatch.model.SeriesData;
import com.teixeira.screenmatch.model.EpisodeData;
import com.teixeira.screenmatch.service.ApiConsumer;
import com.teixeira.screenmatch.service.IDataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private ApiConsumer apiConsumer;

	@Autowired
	private IDataConverter converter;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Aplicação Screenmatch iniciada. Executando CommandLineRunner...");

		String movieTitle = "Gilmore Girls";
		String apiKey = "6585022c";

		String apiUrl = "https://www.omdbapi.com/?t=" + movieTitle.replace(" ", "+") + "&apikey=" + apiKey;

		System.out.println("Consumindo API em: " + apiUrl);

		try {
			String jsonResponse = apiConsumer.obtainData(apiUrl);

			System.out.println("\nResposta JSON da API (Dados da Série):");
			System.out.println(jsonResponse);

			SeriesData seriesData = converter.obtainData(jsonResponse, SeriesData.class);

			System.out.println("\nDados da Série (Objeto Java):");
			System.out.println("Título: " + seriesData.title());
			System.out.println("Total de Temporadas: " + seriesData.totalSeasons());
			System.out.println("Avaliação IMDb: " + seriesData.imdbRating());

			int seasonNumber = 0;

			while (true) {
				System.out.print("Qual temporada você gostaria de visualizar? (1-" + seriesData.totalSeasons() + "): ");
				try {
					seasonNumber = scanner.nextInt();
					if (seasonNumber <= 0 || seasonNumber > seriesData.totalSeasons()) {
						System.out.println("Temporada inválida. Por favor, insira um número entre 1 e " + seriesData.totalSeasons() + ".");
					} else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("Entrada inválida. Por favor, insira um número inteiro para a temporada.");
					scanner.next();
				}
			}

			System.out.println("Você selecionou a temporada: " + seasonNumber);

			// NOVO CÓDIGO PARA SOLICITAR E BUSCAR UM EPISÓDIO ESPECÍFICO
			int episodeNumber = 0;

			while (true) {
				System.out.print("Qual número do episódio você gostaria de visualizar? ");
				try {
					episodeNumber = scanner.nextInt();
					if (episodeNumber <= 0) {
						System.out.println("Número do episódio inválido. Por favor, insira um número inteiro positivo.");
					} else {
						break;
					}
				} catch (InputMismatchException e) {
					System.out.println("Entrada inválida. Por favor, insira um número inteiro para o episódio.");
					scanner.next();
				}
			}

			System.out.println("\nBuscando informações para o episódio " + episodeNumber + " da temporada " + seasonNumber + "...");

			String episodeApiUrl = "https://www.omdbapi.com/?t=" + movieTitle.replace(" ", "+") +
					"&Season=" + seasonNumber + "&Episode=" + episodeNumber + "&apikey=" + apiKey;

			String episodeJsonResponse = apiConsumer.obtainData(episodeApiUrl);

			if (episodeJsonResponse.contains("\"Response\":\"False\"")) {
				System.out.println("Episódio " + episodeNumber + " não encontrado na temporada " + seasonNumber + " para a série " + movieTitle + ".");
			} else {
				EpisodeData episodeData = converter.obtainData(episodeJsonResponse, EpisodeData.class);

				System.out.println("\nDetalhes do Episódio:");
				System.out.println("  Episódio " + episodeData.episodeNumber() +
						": " + episodeData.title() +
						" (Avaliação: " + episodeData.rating() +
						", Lançamento: " + episodeData.releaseDate() + ")");
			}

		} catch (RuntimeException e) {
			System.err.println("Ocorreu um erro ao consumir ou processar a API: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("\nExecução do CommandLineRunner finalizada.");
		scanner.close();
	}
}
