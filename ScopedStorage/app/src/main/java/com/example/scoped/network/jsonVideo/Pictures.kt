package com.example.scoped.network.jsonVideo

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Pictures(
        val id: Long,
        val nr: Int,
        val link: String
)
