package com.example.scoped.network.jsonVideo

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Tags(
        val tag: Boolean? = null
)