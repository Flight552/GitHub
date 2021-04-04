package com.example.moshiapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moshiapp.R
import com.example.moshiapp.movieData.MovieClass
import com.example.moshiapp.movieData.MovieDiffUtil
import com.example.moshiapp.movieData.MovieViewHolder
import com.example.moshiapp.utils.inflate

class MovieAdapter(private val onItemClicked: (position: Int) -> Unit): RecyclerView.Adapter<MovieViewHolder>() {

    private val differ = AsyncListDiffer<MovieClass>(this, MovieDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.movie_item)) { position ->
            onItemClicked(position)
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateList(list: List<MovieClass>) {
        differ.submitList(list)
    }
}