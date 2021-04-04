package com.skillbox.github.repository

import com.skillbox.github.data.ServerItemsWrapper
import com.skillbox.github.data.Singleton
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<ServerItemsWrapper<RemoteUser>>

    @GET("user/{owner}")
    fun showUser(
        @Path("owner") id: Long
    ): Call<RemoteUser>

    @GET("user")
    fun getAuthenticatedUser (
    ) : Call<RemoteUser>

}