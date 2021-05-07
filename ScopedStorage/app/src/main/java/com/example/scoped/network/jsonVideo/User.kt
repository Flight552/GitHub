package com.example.scoped.network.jsonVideo

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class User(
 val id: Long,
 val name: String,
 val url: String
)