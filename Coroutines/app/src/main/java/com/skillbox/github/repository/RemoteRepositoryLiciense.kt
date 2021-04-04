package com.skillbox.github.repository

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRepositoryLicense(
    val key: String? = null,
    val name: String? = null,
    val url: String? = null,
    val spdx_id: String? = null,
    val node_id: String? = null,
    val html_url: String? = null
)