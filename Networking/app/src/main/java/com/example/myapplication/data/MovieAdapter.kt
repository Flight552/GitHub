package com.example.myapplication.data

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.utils.inflate

class MovieAdapter: RecyclerView.Adapter<MovieViewHolder>() {

    private val differ = AsyncListDiffer<Movie>(this, MovieDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.movie_view))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateList(list: List<Movie>) {
        differ.submitList(list)
    }

}