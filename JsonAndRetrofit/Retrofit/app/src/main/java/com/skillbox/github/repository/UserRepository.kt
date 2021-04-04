package com.skillbox.github.repository

import android.util.Log
import com.skillbox.github.data.ServerItemsWrapper
import com.skillbox.github.network.NetworkRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException

class UserRepository {

    fun searchUsers(
        query: String,
        onComplete: (List<RemoteUser>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        NetworkRetrofit.gitHubApi.searchUsers(query)
            .enqueue(object : Callback<ServerItemsWrapper<RemoteUser>> {
                override fun onResponse(
                    call: Call<ServerItemsWrapper<RemoteUser>>,
                    response: Response<ServerItemsWrapper<RemoteUser>>
                ) {
                    if (response.isSuccessful) {
                     //   Log.d("User_Repo", "${response.body()}")
                        onComplete(response.body()?.items.orEmpty())
                    } else {
                        onError(RuntimeException("incorrect status code"))
                    }
                }

                override fun onFailure(call: Call<ServerItemsWrapper<RemoteUser>>, t: Throwable) {
                    onError(t)
                }

            })
    }

    fun showSingleUser(id: Long,
        onComplete: (RemoteUser) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Log.d("User_Repo", "Start Show User")
      NetworkRetrofit.gitHubApi.showUser(id)
          .enqueue(object : Callback<RemoteUser>{
              override fun onResponse(
                  call: Call<RemoteUser>,
                  response: Response<RemoteUser>
              ) {
                  Log.d("User_Repo", "Response ${response.isSuccessful}")
                  if(response.isSuccessful) {
                      var idUser = -1L
                      val responseID = response.body()?.id
                      if(responseID != null) {
                          idUser = responseID
                      }
                      Log.d("User_Repo", "User - ${response.body()?.id}")
                      val nameUser = response.body()?.name.orEmpty()
                      val avatarUser = response.body()?.avatar.orEmpty()
                      onComplete(RemoteUser(id = idUser, name = nameUser, avatar = avatarUser))
                  } else {
                      onError(RuntimeException())
                  }
              }

              override fun onFailure(call: Call<RemoteUser>, t: Throwable) {
                  Log.d("User_Repo", "On Error ${t.toString()}")
                  onError(t)
              }

          })
    }

    fun showAuthenticatedUser(
        onComplete: (RemoteUser) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        NetworkRetrofit.gitHubApi.getAuthenticatedUser().enqueue(
            object: Callback<RemoteUser> {
                override fun onResponse(call: Call<RemoteUser>, response: Response<RemoteUser>) {
                    if(response.isSuccessful) {
                        val nameUser = response.body()?.name.orEmpty()
                        val avatarUser = response.body()?.avatar.orEmpty()
                        onComplete(RemoteUser(name = nameUser, avatar = avatarUser))
                    } else {
                        onError(RuntimeException())
                    }
                }

                override fun onFailure(call: Call<RemoteUser>, t: Throwable) {
                    onError(t)
                }

            }
        )
    }
}