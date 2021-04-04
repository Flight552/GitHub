package com.example.myapplication.data

import android.util.Log
import com.example.myapplication.network.Network
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MovieRepository {

    //http://www.omdbapi.com/?apikey=8a159ccc&s=
    var movieList = emptyList<Movie>()

    fun searchMovie(
        type: String, title: String, year: String, callBack: (List<Movie>) -> Unit,
        errorCallback: (e: Throwable) -> Unit
    ): Call {
            return Network.getSearchMovieCall(type, title, year).apply {
                enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("Connection Exception", "Connection error ${e.message}", e)
                        errorCallback(e)
                    }

                    override fun onResponse(call: Call, response: Response) {
                        if (response.isSuccessful) {
                            val getJSON = response.body?.string().orEmpty()
                            movieList = getParsedMovieList(getJSON)
                            callBack(movieList)
                        } else {
                            errorCallback(IOException())
                        }
                    }
                })
            }
        }

    private fun getParsedMovieList(responseJSON: String): List<Movie> {
        var movies: List<Movie> = emptyList()
        try {
            val jsonObject = JSONObject(responseJSON)

            //проверка на корректное возвращения результатов поиска
            movies = if (!jsonObject.isNull("Search")) {
                val movieArray = jsonObject.getJSONArray("Search")
                (0 until movieArray.length()).map { index ->
                    movieArray.getJSONObject(index)
                }.map { movieObject ->
                    val title = movieObject.getString("Title")
                    val type = movieObject.getString("Type")
                    val year = movieObject.getString("Year")
                    val imdbID = movieObject.getString("imdbID")
                    val poster = movieObject.getString("Poster")
                    Movie(imdbID, title, type, year, poster)
                }
            } else {
                listOf(Movie(imdbID = "Nothing to be found"))
            }
        } catch (e: JSONException) {
            Log.e("JSON Exception", "json error ${e.message}", e)
            return listOf(
                Movie(imdbID = "Common error")
            )
        }
        return movies
    }
}

