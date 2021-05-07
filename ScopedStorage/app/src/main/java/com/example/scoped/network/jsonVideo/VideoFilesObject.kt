package com.example.scoped.network.jsonVideo

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class VideoFilesObject(
        val id: Long,
        val quality: String,
        val file_type: String,
        val width: Int,
        val height: Int,
        val link: String
)