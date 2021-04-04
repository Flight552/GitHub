package com.example.moshiapp.movieData

import com.squareup.moshi.Json

enum class MovieRating {
    @Json(name = "G")
    GENERAL,
    PG,
    @Json(name = "PG-13")
    PG_13,
    R,
    @Json(name = "NC-17")
    NC_17,
    @Json(name = "TV-PG")
    TV
}