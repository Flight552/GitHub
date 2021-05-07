package com.example.scoped.data

import android.net.Uri
import android.widget.VideoView
import java.io.File
import java.sql.Time

data class Video(
        val id: Long,
        val title: String,
        val duration: String?,
        val path: Uri
)
