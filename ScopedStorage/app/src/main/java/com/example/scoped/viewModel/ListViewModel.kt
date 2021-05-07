package com.example.scoped.viewModel

import android.app.Application
import android.app.RecoverableSecurityException
import android.app.RemoteAction
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import com.example.scoped.R
import com.example.scoped.data.Video
import com.example.scoped.repository.VideoRepository
import com.example.scoped.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ListViewModel(application: Application): AndroidViewModel(application) {

    private val videoRepository = VideoRepository(application)

    private val permissionsGrantedMutableLiveData = MutableLiveData(true)
    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    private val videosMutableLiveData = MutableLiveData<List<Video>>()
    private val recoverableActionMutableLiveData = MutableLiveData<RemoteAction>()

    private var pendingDeleteID: Long? = null

    private var isObservingStarted: Boolean = false

    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent

    val videoLiveData: LiveData<List<Video>>
        get() = videosMutableLiveData

    val permissionsGrantedLiveData: LiveData<Boolean>
        get() = permissionsGrantedMutableLiveData

    val recoverableActionLiveData: LiveData<RemoteAction>
        get() = recoverableActionMutableLiveData

    override fun onCleared() {
        super.onCleared()
        videoRepository.unregisterObserver()
    }

    fun updatePermissionState(isGranted: Boolean) {
        if(isGranted) {
            permissionsGranted()
        } else {
            permissionsDenied()
        }
    }
    fun onDelete(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    videoRepository.deleteVideo(id)
                    pendingDeleteID = null
                } catch (e: Throwable) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && e is RecoverableSecurityException) {
                        pendingDeleteID = id
                     recoverableActionMutableLiveData.postValue(e.userAction)
                    }
                    Log.e("ListViewModel", "error deleting movie: $e")
                }
            }
        }
    }

    fun confirmDelete() {
        pendingDeleteID?.let {
            onDelete(it)
        }
    }
    fun declineDelete() {
        pendingDeleteID = null
    }


    private fun loadVideos() {
        viewModelScope.launch {
            try {
                val videos = videoRepository.getAllVideosFromStorage()
                Log.d("ListViewModel", "list videos from repo $videos")
                videosMutableLiveData.postValue(videos)
            } catch (t: Throwable) {
                Log.d("ListViewModel", "Some catch error")
                Log.d("ListViewModel", "$t")
                videosMutableLiveData.postValue(emptyList())
                toastSingleLiveEvent.postValue(R.string.video_list_error)
            }
        }
    }

    fun permissionsGranted() {
        loadVideos()
        if(isObservingStarted.not()) {
            videoRepository.observeVideos { loadVideos() }
            isObservingStarted = true
        }
        permissionsGrantedMutableLiveData.postValue(true)
    }

    fun permissionsDenied() {
        permissionsGrantedMutableLiveData.postValue(false)
    }

}