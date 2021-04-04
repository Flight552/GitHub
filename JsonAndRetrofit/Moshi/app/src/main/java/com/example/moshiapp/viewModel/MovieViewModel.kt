package com.example.moshiapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moshiapp.movieData.MovieClass
import com.example.moshiapp.movieData.MovieRepository
import okhttp3.Call

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository()

    private var currentCall: Call? = null
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData
    private val movieListViewModel = MutableLiveData<List<MovieClass>>()
    val movieList: LiveData<List<MovieClass>>
        get() = movieListViewModel

    fun search(title: String = "", id: String = "") {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(title, id, {
            movies ->
            isLoadingLiveData.postValue(false)
            movieListViewModel.postValue(movies)
            currentCall = null
        }) {
            error ->
            Log.e("JsonError", "${error.message}")
        }
    }

    fun sendToJson(movie: MovieClass) {
        repository.toJsonMoshi(movie) {
            error -> Log.e("JsonError", "${error.message}")

        }
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}