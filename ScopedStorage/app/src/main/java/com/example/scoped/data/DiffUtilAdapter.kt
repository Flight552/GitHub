package com.example.scoped.data

import androidx.recyclerview.widget.DiffUtil

class DiffUtilAdapter: DiffUtil.ItemCallback<Video>() {

    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
       return oldItem == newItem
    }
}