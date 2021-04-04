package com.skillbox.multithreading.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.multithreading.R
import com.skillbox.multithreading.networking.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    // create movie list

    private var moviesList = emptyList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_movie, parent, false)
        return MovieHolder(inflater)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateList(list: List<Movie>) {
        moviesList = list
    }

    class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMovieName = view.findViewById<TextView>(R.id.tv_movieName)
        private val tvMovieYear: TextView = view.findViewById(R.id.tv_movieYear)

        fun bind(movie: Movie) {
            tvMovieName.text = movie.title
            tvMovieYear.text = movie.year.toString()
        }
    }

}