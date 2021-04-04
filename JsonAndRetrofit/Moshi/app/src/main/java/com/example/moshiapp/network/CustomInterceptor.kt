package com.example.moshiapp.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalURL = originalRequest.url
        val modifiedURL = originalURL.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()
        val response = originalRequest.newBuilder()
            .url(modifiedURL)
            .build()
        Log.d("Movie_Interceptor", "Request received ${modifiedURL.toString()}")
        return chain.proceed(response)
    }
}