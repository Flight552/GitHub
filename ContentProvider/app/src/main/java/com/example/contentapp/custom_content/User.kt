package com.example.contentapp.custom_content

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val id: Long,
    val name:String,
    val age: Int
)