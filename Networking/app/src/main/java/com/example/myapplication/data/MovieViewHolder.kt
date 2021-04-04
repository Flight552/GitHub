package com.example.myapplication.data

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvImagePoster = view.findViewById<ImageView>(R.id.ivPosterMovie)
    private val tvMovieID = view.findViewById<TextView>(R.id.tvID)
    private val tvMovieTitle = view.findViewById<TextView>(R.id.tvMovieTitle)
    private val tvMovieGenre = view.findViewById<TextView>(R.id.tvMovieGenre)
    private val tvMovieYear = view.findViewById<TextView>(R.id.tvMovieYear)


    fun bind(movie: Movie) {
        val id = "ID: ${movie.imdbID}"
        val title = "Title: ${movie.title}"
        val type = "Genre: ${movie.type}"
        val year = "Release year: ${movie.year}"
        val image = movie.poster
        Glide.with(itemView)
            .load(image)
            .into(tvImagePoster)

        tvMovieID.text = id
        tvMovieTitle.text = title
        tvMovieGenre.text = type
        tvMovieYear.text = year
    }
}