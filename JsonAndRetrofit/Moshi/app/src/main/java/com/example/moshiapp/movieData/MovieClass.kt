package com.example.moshiapp.movieData

import com.example.moshiapp.movieData.MovieRating
import com.example.moshiapp.movieData.MovieScore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class MovieClass(
    val imdbID: String = "N/A",
    val Title: String = "N/A",
    val Year: String = "N/A",
    val Genre: String = "N/A",
    val Poster: String = "N/A",
    val Rated: MovieRating = MovieRating.GENERAL,
    val Ratings: Map<String, String>,
    val Released: String = "N/A",
    val Runtime: String = "N/A",
    val Director: String = "N/A",
    val Writer: String = "N/A",
    val Actors: String = "N/A",
    val Plot: String = "N/A",
    val Language: String = "N/A",
    val Country: String = "N/A",
    val Awards: String = "N/A",
    val Metascore: String = "N/A",
    val imdbRating: String = "N/A",
    val imdbVotes: String = "N/A",
    val DVD: String = "N/A",
    val BoxOffice: String = "N/A",
    val Production: String = "N/A",
    val Website: String = "N/A",
    val Response: String = "True"
)