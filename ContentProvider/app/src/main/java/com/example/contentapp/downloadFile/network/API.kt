package com.example.contentapp.downloadFile.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface API {
    @GET
    suspend fun get(
        @Url url: String
    ) : ResponseBody
}