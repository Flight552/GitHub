package com.skillbox.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    private val repository = UserRepository()


    private val singleUserLiveData = MutableLiveData<RemoteUser>()
    val singleUser: LiveData<RemoteUser>
        get() = singleUserLiveData

    private val liveUser = MutableLiveData<List<RemoteUser>>(emptyList())
    val user: LiveData<List<RemoteUser>>
        get() = liveUser

    private val liveData = MutableLiveData<Boolean>(false)
    val data: LiveData<Boolean>
        get() = liveData

    fun userList() {
        liveData.postValue(true)
        repository.searchUsers("test", { user ->
            liveData.postValue(false)
            liveUser.postValue(user)
        }) {
            liveData.postValue(false)
            liveUser.postValue(emptyList())
        }
    }

    fun userInfo(id: Long) {
        liveData.postValue(true)
        repository.showSingleUser(id, { user ->
            liveData.postValue(false)
            singleUserLiveData.postValue(user)
        }) {
            liveData.postValue(false)
            singleUserLiveData.postValue(RemoteUser())
        }
    }
    fun authenticatedUser() {
        liveData.postValue(true)
        repository.showAuthenticatedUser({
            user ->
            liveData.postValue(false)
            singleUserLiveData.postValue(user)
        }) {
            liveData.postValue(false)
            singleUserLiveData.postValue(RemoteUser())
        }
    }
}