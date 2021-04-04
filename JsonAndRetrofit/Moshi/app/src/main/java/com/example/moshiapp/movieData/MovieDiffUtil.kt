package com.example.moshiapp.movieData

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class MovieDiffUtil: DiffUtil.ItemCallback<MovieClass>() {

    override fun areItemsTheSame(oldItem: MovieClass, newItem: MovieClass): Boolean {
        Log.d("Movie_Diff_Util", "Adapter Initialized ${oldItem.imdbID == newItem.imdbID}")
        return oldItem.imdbID == newItem.imdbID
    }

    override fun areContentsTheSame(oldItem: MovieClass, newItem: MovieClass): Boolean {
        Log.d("Movie_Diff_Util", "Adapter Initialized ${oldItem == newItem}")
        return oldItem == newItem
    }

}