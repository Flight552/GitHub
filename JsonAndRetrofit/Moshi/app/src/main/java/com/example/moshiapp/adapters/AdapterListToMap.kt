package com.example.moshiapp.adapters

import com.example.moshiapp.movieData.MovieClass
import com.example.moshiapp.movieData.MovieRating
import com.example.moshiapp.movieData.MovieScore
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

class AdapterListToMap {

    @JsonClass(generateAdapter = true)
    data class ListMovie(
        val imdbID: String = "N/A",
        val Title: String = "N/A",
        val Year: String = "N/A",
        val Genre: String = "N/A",
        val Poster: String = "N/A",
        val Rated: MovieRating = MovieRating.GENERAL,
        val Ratings: List<MovieScore> = emptyList(),
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
    ) {}

    @FromJson
    fun getNewMapMovieClass(oldMovieClass: ListMovie): MovieClass {
        val newMap: Map<String, String> = (oldMovieClass.Ratings.indices).map { index ->
            oldMovieClass.Ratings[index].Source to oldMovieClass.Ratings[index].Value
        }.toMap()

        return MovieClass(
            imdbID = oldMovieClass.imdbID,
            Title = oldMovieClass.Title,
            Year = oldMovieClass.Year,
            Genre = oldMovieClass.Genre,
            Rated = oldMovieClass.Rated,
            Ratings = newMap,
            Poster = oldMovieClass.Poster
        )
    }

    @ToJson
    fun addMovieToJson(movieClass: MovieClass) : ListMovie {
        val mapToList: List<MovieScore> = movieClass.Ratings.map {map ->
            MovieScore(map.key, map.value)
        }
        return ListMovie(
            imdbID = movieClass.imdbID,
            Title = movieClass.Title,
            Year = movieClass.Year,
            Genre = movieClass.Genre,
            Rated = movieClass.Rated,
            Ratings = mapToList,
            Poster = movieClass.Poster
        )
    }


}