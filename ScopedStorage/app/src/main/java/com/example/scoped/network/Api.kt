package com.example.scoped.network

import com.example.scoped.network.jsonVideo.VideoJSON
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Api {
    @GET("/videos/videos/{id}")
    suspend fun getVideo(
            @Path("id") id: Long
    ) : VideoJSON

    @GET
    suspend fun saveVideo(@Url url: String) : ResponseBody
}