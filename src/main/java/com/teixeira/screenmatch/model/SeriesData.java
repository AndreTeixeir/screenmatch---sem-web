package com.teixeira.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Java Record to represent series data obtained from the API.
 * @JsonIgnoreProperties(ignoreUnknown = true) ensures that unmapped JSON properties are ignored,
 * preventing errors if the API returns extra fields.
 * @JsonAlias allows mapping a JSON field with a different name to the record's field.
 */
@JsonIgnoreProperties(ignoreUnknown = true) // Added ignoreUnknown = true to prevent errors with unknown fields
public record SeriesData(@JsonAlias("Title") String title, // Mapped "Title" from JSON to 'title'
                         @JsonAlias("totalSeasons") Integer totalSeasons, // Mapped "totalSeasons" to 'totalSeasons'
                         @JsonAlias("imdbRating") String imdbRating) { // Mapped "imdbRating" to 'imdbRating'
}
