package com.example.scoped.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.scoped.R

class VideoAdapter(private val onDeleteVideo: (id: Long) -> Unit) : RecyclerView.Adapter<VideoViewHolder>() {

    private val differ = AsyncListDiffer<Video>(this, DiffUtilAdapter())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false)
        Log.d("VideoAdapter", "view = ${view}")
        return VideoViewHolder(view, onDeleteVideo)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = differ.currentList[position]
        holder.bind(video)
    }

    override fun getItemCount(): Int {
        Log.d("VideoAdapter", "size = ${differ.currentList.size}")
        return differ.currentList.size
    }

    fun updateList(list: List<Video>) {
        differ.submitList(list)
    }

}