package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Movie
import com.example.myapplication.data.MovieRepository
import okhttp3.Call

class MovieListViewModel : ViewModel() {

    private val repository = MovieRepository()

    private var currentCall: Call? = null
    private val movieListViewModel = MutableLiveData<List<Movie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private var error: Boolean = false

    val isError: Boolean
        get() = error

    val movieList: LiveData<List<Movie>>
        get() = movieListViewModel

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(type: String, title: String, year: String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(type, title, year, { movies ->
            isLoadingLiveData.postValue(false)
            error = false
            movieListViewModel.postValue(movies)
            currentCall = null
        }) { getError ->
            Log.e("ErrorCallback", "Возникла ошибка $error", getError)
            error = true
            Log.e("ErrorCallback", "Возникла ошибка $error", getError)
        }
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}