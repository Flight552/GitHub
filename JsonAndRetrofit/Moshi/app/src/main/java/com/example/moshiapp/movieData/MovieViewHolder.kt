package com.example.moshiapp.movieData

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moshiapp.R

class MovieViewHolder(view: View, onItemClicked: (position: Int) -> Unit): RecyclerView.ViewHolder(view){
    private val tvImagePoster = view.findViewById<ImageView>(R.id.ivPosterMovie)
    private val tvMovieID = view.findViewById<TextView>(R.id.tvID)
    private val tvMovieTitle = view.findViewById<TextView>(R.id.tvMovieTitle)
    private val tvMovieGenre = view.findViewById<TextView>(R.id.tvMovieGenre)
    private val tvMovieYear = view.findViewById<TextView>(R.id.tvMovieYear)
    init {
        view.setOnClickListener {
            onItemClicked(adapterPosition)
        }
    }

    fun bind(movieClass: MovieClass) {
        val id = "ID: ${movieClass.imdbID}"
        val title = "Title: ${movieClass.Title}"
        val type = "Genre: ${movieClass.Genre}"
        val year = "Release year: ${movieClass.Year}"
        val image = movieClass.Poster
        Glide.with(itemView)
            .asDrawable()
            .load(image)
            .into(tvImagePoster)

        tvMovieID.text = id
        tvMovieTitle.text = title
        tvMovieGenre.text = type
        tvMovieYear.text = year
    }
}