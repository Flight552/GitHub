package com.example.contentapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Contact(
    val id: Long,
    val name: String,
    val surname: String,
    val phone: List<String>,
    val email: List<String> = emptyList()
)