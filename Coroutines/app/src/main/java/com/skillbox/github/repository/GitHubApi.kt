package com.skillbox.github.repository

import retrofit2.http.*

interface GitHubApi {

    @GET("user/{owner}")
    suspend fun showUser(
        @Path("owner") id: Long
    ): RemoteRepositoryPublicJSON

    @GET("user")
    suspend fun getAuthenticatedUser(
    ): RemoteUser

    @GET("user/repos")
    suspend fun getUserRepository(): List<RemoteRepository>

    @GET("user/starred/{owner}/{repo}")
    fun getStarFromRepository(
        @Path("owner") name: String,
        @Path("repo") repository: String
    ):Boolean

    @PUT("user/starred/{owner}/{repo}")
    fun addStarToRepository(
        @Path("owner") name: String,
        @Path("repo") repository: String
    )

    @DELETE("user/starred/{owner}/{repo}")
    fun deleteStarFromRepository(
        @Path("owner") name: String,
        @Path("repo") repository: String
    )

    @GET("user/following")
   suspend fun getUserFollowers() : List<RemoteUserFollowers>


    @GET("user/starred")
    fun getStarredList()

    @GET("repositories")
    suspend fun getPublicRepos(): List<RemoteRepositoryPublicJSON>

}