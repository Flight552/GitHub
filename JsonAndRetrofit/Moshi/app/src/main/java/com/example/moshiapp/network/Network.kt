package com.example.moshiapp.network

import android.util.Log
import com.example.moshiapp.utils.API_KEY
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object Network {

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomInterceptor(API_KEY))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private fun searchParameters(title: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("s", title)
            .build()
    }

    private fun searchByID(id: String): HttpUrl {
        return HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("i", id)
            .build()
    }

    fun networkSearchMovie(title: String = "", id: String = ""): Call {
        val url = if (title.isNotEmpty())
            searchParameters(title) else searchByID(id)
        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request)
    }

}