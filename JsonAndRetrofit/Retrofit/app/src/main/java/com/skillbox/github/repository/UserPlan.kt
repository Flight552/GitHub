package com.skillbox.github.repository

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPlan(
    val name: String = "",
    val space: Int = 0,
    val private_repos: Int = 0,
    val collaborators: Int = 0
)
