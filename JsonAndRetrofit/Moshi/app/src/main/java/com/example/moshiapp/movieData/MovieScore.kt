package com.example.moshiapp.movieData

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieScore(
    val Source: String,
    val Value: String
)