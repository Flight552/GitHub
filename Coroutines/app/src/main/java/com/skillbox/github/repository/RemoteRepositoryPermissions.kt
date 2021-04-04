package com.skillbox.github.repository

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRepositoryPermissions(
    val admin: Boolean = false,
    val push: Boolean = false,
    val pull: Boolean = false
)