package com.skillbox.github.repository

import android.util.Log
import com.skillbox.github.network.NetworkRetrofit

class UserRepository {
    suspend fun showSingleUser(
        id: Long
    ): RemoteRepositoryPublicJSON {
      return NetworkRetrofit.gitHubApi.showUser(id)
    }


   suspend fun showAuthenticatedUser(
    ): RemoteUser {
        return NetworkRetrofit.gitHubApi.getAuthenticatedUser()
    }

    suspend fun showUserFollowers(): List<RemoteUserFollowers>{
        return NetworkRetrofit.gitHubApi.getUserFollowers()
    }


   suspend fun showUserRepository(
    ) : List<RemoteRepository>{
       return NetworkRetrofit.gitHubApi.getUserRepository()
    }

   suspend fun showPublicRepositories(
    ):List<RemoteRepositoryPublicJSON> {
       return NetworkRetrofit.gitHubApi.getPublicRepos()
    }

    fun checkStars(name: String,
                   repo: String
    ) : Boolean {
       return NetworkRetrofit.gitHubApi.getStarFromRepository(name, repo)
    }


}