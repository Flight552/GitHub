package com.example.scoped.data

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.ThumbnailUtils
import android.util.Log
import android.util.Size
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.scoped.R
import java.io.File

class VideoViewHolder(private val view: View, onDeleteVideo: (id: Long) -> Unit) : RecyclerView.ViewHolder(view) {
    private val tvMovieTitle: TextView = view.findViewById(R.id.tvMovieTitle)
    private val tvMovieLength: TextView = view.findViewById(R.id.tvMovieLength)
    private val videoView: ImageView = view.findViewById(R.id.videoPreview)
    private val btDeleteButton: ImageButton = view.findViewById(R.id.btDeleteMovie)
    private var videoId: Long? = null

    init {
        btDeleteButton.setOnClickListener {
            videoId?.let {
                onDeleteVideo(it)
            }
            Log.d("VideoViewHolder", "CLICK CLICK ${absoluteAdapterPosition}")
        }
    }


    fun bind(video: Video) {
        videoId = video.id
        val videoFileUri = video.path
        val title = "Title: ${video.title}"
        tvMovieTitle.text = title
        val length = "Length: ${video.duration}"
        tvMovieLength.text = length

        Glide.with(view)
                .load(videoFileUri)
                .placeholder(R.drawable.ic_launcher_background)
                .into(videoView)

    }
}