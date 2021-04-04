package com.skillbox.github.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.github.data.ServerItemsWrapper
import kotlinx.coroutines.*

data class AuthenticatedCustomUser(
    val user: RemoteUser? = null,
    val followers: List<RemoteUserFollowers>? = null
)

class UserViewModel : ViewModel() {

    private val job = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val repository = UserRepository()

    private val publicRepositoryLiveData =
        MutableLiveData<List<RemoteRepositoryPublicJSON>>(emptyList())

    val publicRepo: LiveData<List<RemoteRepositoryPublicJSON>>
        get() = publicRepositoryLiveData

    private val userRepositoryLiveData = MutableLiveData<List<RemoteRepository>>(emptyList())
    val userRepo: LiveData<List<RemoteRepository>>
        get() = userRepositoryLiveData

    private val singleUserLiveData = MutableLiveData<RemoteUser>()
    val singleUser: LiveData<RemoteUser>
        get() = singleUserLiveData

    private val singleRepoLiveData = MutableLiveData<RemoteRepositoryPublicJSON>()
    val singleRepo: LiveData<RemoteRepositoryPublicJSON>
        get() = singleRepoLiveData

    private val liveUser = MutableLiveData<List<RemoteUser>>(emptyList())
    val user: LiveData<List<RemoteUser>>
        get() = liveUser

    private val liveData = MutableLiveData<Boolean>(false)
    val data: LiveData<Boolean>
        get() = liveData

    private val authenticatedCustomUser = MutableLiveData<AuthenticatedCustomUser>()
    val customerUser: LiveData<AuthenticatedCustomUser>
        get() = authenticatedCustomUser


    fun userRepository() {
        job.launch {
            liveData.postValue(true)
            try {
                val listRepositories = repository.showUserRepository()
                userRepositoryLiveData.postValue(listRepositories)
            } catch (t: Throwable) {
                liveUser.postValue(emptyList())
            } finally {
                liveData.postValue(false)

            }
        }
    }

    fun publicRepository() {
        job.launch {
            liveData.postValue(true)
            try {
                val publicRepositories = repository.showPublicRepositories()
                publicRepositoryLiveData.postValue(publicRepositories)

            } catch (t: Throwable) {
                publicRepositoryLiveData.postValue(emptyList())

            } finally {
                liveData.postValue(false)
            }
        }
    }

    fun userInfo(id: Long) {
        job.launch {
            liveData.postValue(true)
            try {
                val userJson = repository.showSingleUser(id)
                singleRepoLiveData.postValue(userJson)
            } catch (t: Throwable) {
                singleRepoLiveData.postValue(RemoteRepositoryPublicJSON())
            } finally {
                liveData.postValue(false)
            }
        }
    }

   suspend fun authenticatedUser() {
        job.launch {
            liveData.postValue(true)
            try {
                val userJob = job.async {
                        repository.showAuthenticatedUser()
                }
                val userFollowers = job.async {
                       repository.showUserFollowers()
                }
                authenticatedCustomUser.postValue(AuthenticatedCustomUser(user = userJob.await(), followers = emptyList()))
            } catch (t: Throwable) {
               Log.d("UserViewModel", "Async error")
            } finally {
                liveData.postValue(false)
            }
        }
    }


    fun checkForStar(name: String, repo: String) {
        val scope = viewModelScope + SupervisorJob()
        scope.launch {
            liveData.postValue(true)
            try {
                val hasStar = repository.checkStars(name, repo)
                liveData.postValue(hasStar)
            } catch (t: Throwable) {
                liveData.postValue(false)
            } finally {
                liveData.postValue(false)
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}