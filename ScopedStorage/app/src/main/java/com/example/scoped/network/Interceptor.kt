package com.example.scoped.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()
                .addHeader("Authorization", "563492ad6f917000010000015d2d118fdedf4090a4b723205492febd")
                .build()
        val originalURL = originalRequest.url
        val response = originalRequest.newBuilder()
                .url(originalURL)
                .build()
        return chain.proceed(response)
    }
}