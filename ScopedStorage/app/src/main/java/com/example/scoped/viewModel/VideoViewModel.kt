package com.example.scoped.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.scoped.R
import com.example.scoped.data.Video
import com.example.scoped.repository.VideoRepository
import com.example.scoped.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

//video id

private val testUrlVideo1 = 1408643
private val testUrlVideo2 = 2499611
private val testUrlVideo3 = 4823938


class VideoViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = VideoRepository(app)
    private val isMovieMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isMovie: LiveData<Boolean>
        get() = isMovieMutableLiveData

    private val toastSingleLiveEvent = SingleLiveEvent<Int>()
    val toastLiveData: LiveData<Int>
        get() = toastSingleLiveEvent


    suspend fun saveVideo(id: String, title: String) {
        viewModelScope.launch {
            try {
                repository.downloadVideo(id, title)
                isMovieMutableLiveData.postValue(true)
            } catch (e: HttpException) {
                isMovieMutableLiveData.postValue(false)
                toastSingleLiveEvent.postValue(R.string.video_find_error)
            }
        }
    }

    fun checkEditText(id: String?, title: String?) : Boolean {
        return repository.checkForFields(id, title)
    }
}