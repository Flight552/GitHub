package com.example.scoped.network.jsonVideo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoJSON(
        val id: Long,
        val width: Int,
        val height: Int,
        val duration: Int,
        val full_res: Boolean? = null,
        val tags: List<Tags>,
        val url: String,
        val image: String,
        val avg_color: Boolean? = null,
        val user: User,
        val video_files: List<VideoFilesObject>,
        val video_pictures: List<Pictures>
)