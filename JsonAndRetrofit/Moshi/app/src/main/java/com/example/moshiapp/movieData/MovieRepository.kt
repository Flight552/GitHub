package com.example.moshiapp.movieData

import android.util.Log
import com.example.moshiapp.adapters.AdapterListToMap
import com.example.moshiapp.network.Network
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {
    private var movieList = emptyList<MovieClass>()

    fun searchMovie(
        title: String = "",
        id: String = "",
        callBack: (List<MovieClass>) -> Unit,
        errorCallback: (e: Throwable) -> Unit
    ): Call {
        return Network.networkSearchMovie(title, id).apply {
            enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    errorCallback(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val json = response.body?.string().orEmpty()
                        movieList = getJSONMovieListMoshi(json) { e ->
                            errorCallback(e)
                        }
                        callBack(movieList)
                    } else {
                        errorCallback(IOException())
                    }
                }
            })
        }

    }

    private fun getJSONMovieListMoshi(
        jsonString: String,
        errorCallback: (e: Throwable) -> Unit
    ): List<MovieClass> {
        val movies: MutableList<MovieClass> = mutableListOf()
        try {
            val jsonObject = JSONObject(jsonString)
            if (!jsonObject.isNull("Search")) {
                val movieArray = jsonObject.getJSONArray("Search")
                (0 until movieArray.length()).map { index ->
                    movieArray.getJSONObject(index)
                }.map { movieObject ->
                    //moshi для массива
                    moshiFun(movieObject.toString(), movies, errorCallback)
                }
            } else {
                //moshi для массива для одного объекта
                moshiFun(jsonString, movies, errorCallback)
            }
        } catch (e: JSONException) {
            Log.e("JSON Exception", "json error ${e.message}", e)
            return emptyList()
        }
        return movies
    }

    private fun moshiFun(jsonString: String, movies: MutableList<MovieClass>, errorCallback: (e: Throwable) -> Unit) {
        val moshi = Moshi.Builder()
            .add(AdapterListToMap())
            .build()
        val adapter = moshi.adapter(MovieClass::class.java).nonNull()
        try {
            val movie = adapter.fromJson(jsonString)
            //Parse back to Json
            if(movie != null) {
                movies.add(0, movie)
            }
        } catch (e: JsonDataException) {
            errorCallback(e)
        }
    }

    // для парсинга в json после добавления рейтинга
     fun toJsonMoshi(movieMapClass: MovieClass, errorCallback: (e: Throwable) -> Unit) {
        val moshi = Moshi.Builder()
            .add(AdapterListToMap())
            .build()
        val adapter = moshi.adapter(MovieClass::class.java).nonNull()
        try {
            Log.d("movie_repository", "To Json - ${adapter.toJson(movieMapClass)}")
        } catch (e: JsonDataException) {
            errorCallback(e)
        }
    }

}