package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.data.Movie
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Network {

    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomInterceptor("8a159ccc"))
        .addNetworkInterceptor(FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    fun getSearchMovieCall(type: String, title: String, year: String): Call {
        val request = Request.Builder()
            .get()
            .url(searchMovieByParameters(title, type, year))
            .build()
        return client.newCall(request)
    }

    private fun searchMovieByParameters(title: String, type: String, year: String): HttpUrl {
        // если год и тип не введен, используется дефолтное значение
        return HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("s", title)
            .addQueryParameter("type", type)
            .addQueryParameter("y", year)
            .build()
    }
}