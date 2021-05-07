package com.example.scoped.repository

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import com.example.scoped.data.Video
import com.example.scoped.network.Network
import com.example.scoped.network.jsonVideo.VideoJSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.FileNotFoundException
import java.io.IOException


class VideoRepository(
    private val context: Context
) {

    private var observer: ContentObserver? = null

    fun checkForFields(editText1: String?, editText2: String?): Boolean {
        Log.e(
            "VideoRepositoryDownloadVideo",
            " Video file uri ${editText1.isNullOrBlank() || editText2.isNullOrBlank()}"
        )
        return editText1.isNullOrBlank() || editText2.isNullOrBlank()
    }

    suspend fun downloadVideo(movieID: String, title: String) {
        withContext(Dispatchers.IO) {
            val id = movieID.toLong()
            val uri = saveVideoDetails(title)
            val videoJson = Network.api.getVideo(id) //video id from user
            Log.e("VideoRepositoryDownloadVideo", " Video file uri $uri")
            getDownloadVideoLink(videoJson, uri)
            makeImageVisible(uri)
        }
    }

    private suspend fun getDownloadVideoLink(videoJson: VideoJSON, uri: Uri) {
        var id: Long? = null
        withContext(Dispatchers.IO) {
            val videoUrl = videoJson.video_files[0].link
            Log.d("VideoRepositoryDownloadVideo", "video url $videoUrl")
            try {
               id = context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                    Network.api.saveVideo(videoUrl)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                }
            } catch (e: IOException) {
               id?.let { deleteVideoDetails(it) } // delete file on downloading error
            }
        }
    }

    fun deleteVideoDetails(id: Long) {
        val contentUri =
            ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
        context.contentResolver.delete(contentUri, null, null)
    }

    private fun saveVideoDetails(name: String): Uri {

        val volume = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }
        val videoUri = MediaStore.Video.Media.getContentUri(volume)
        val videoDetails = ContentValues().apply {
            put(MediaStore.Video.Media.DISPLAY_NAME, name)
            put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            put(MediaStore.Video.Media.RELATIVE_PATH, "Movies")
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                put(MediaStore.Video.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(videoUri, videoDetails)!!
    }

    suspend fun getAllVideosFromStorage(): List<Video> {
        val listOfMovies: MutableList<Video> = mutableListOf()
        withContext(Dispatchers.IO) {
            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
            )

        }?.use { cursor ->
            Log.d("VideoRepositoryGetDownloadVideoLink", "cursor $cursor")
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))
                val duration =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION))
                val contentUri =
                    ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                Log.d(
                    "VideoRepositoryGetDownloadVideoLink",
                    "id: $id, name: $name, duration: $duration, contentUri: $contentUri"
                )
                listOfMovies.add(Video(id, name, duration, contentUri))
            }
        }
        return listOfMovies
    }

    fun observeVideos(onChange: () -> Unit) {
        observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean) {
                super.onChange(selfChange)
                onChange()
            }
        }
        context.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            observer!!
        )
    }

    fun unregisterObserver() {
        observer?.let {
            context.contentResolver.unregisterContentObserver(it)
        }
    }

    suspend fun deleteVideo(id: Long) {
        withContext(Dispatchers.IO) {
            val contentUri =
                ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
            context.contentResolver.delete(contentUri, null, null)
        }
    }

    private fun makeImageVisible(imageUri: Uri) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(imageUri, imageDetails, null, null)
    }
}