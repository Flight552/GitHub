package com.skillbox.github.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class GitHubInterceptor(private val access_token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()
            .addHeader("Authorization", "token $access_token")
            .build()
        val originalURL = originalRequest.url
        Log.d("Network_Token", "Request -  $originalURL")
        val response = originalRequest.newBuilder()
            .url(originalURL)
            .build()
        Log.d("Network_Token", "Response - $response")
        return chain.proceed(response)
    }

}
