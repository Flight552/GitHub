package com.example.myapplication.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalURL = originalRequest.url
        val modifiedURL = originalURL.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val response = originalRequest.newBuilder()
            .url(modifiedURL)
            .build()
        return chain.proceed(response)
    }
}