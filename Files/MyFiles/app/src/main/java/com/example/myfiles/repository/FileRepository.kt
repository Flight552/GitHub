package com.example.myfiles.repository

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myfiles.network.Networking
import kotlinx.coroutines.coroutineScope
import java.io.File

class FileRepository(private val context: Context) {

    val sharedPref: SharedPreferences? =
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
    private val sharedFirst: SharedPreferences? =
        context.getSharedPreferences(FIRST_LAUNCH_FLAG, Context.MODE_PRIVATE)
    private var myFile: File? = null

    suspend fun getFile(fileURL: String) {
        coroutineScope {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return@coroutineScope
            } else {
                createMyFile()
            }
            try {
                val file = myFile?.outputStream()?.let { stream ->
                    Networking.api
                        .get(fileURL)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(stream)
                        }
                }
                Toast.makeText(context, "File $FILE_NAME is loaded successfully", Toast.LENGTH_LONG)
                    .show()
                createSharedPrefs(file)
            } catch (t: Throwable) {
                try {
                    myFile?.delete()
                } catch (t: Throwable) {
                    Log.d("FileRepository", "Delete error. Unable delete file $myFile")
                }
            }

        }
    }


    private suspend fun createSharedPrefs(file: Long?) {
        coroutineScope {
            if (file != null) {
                sharedPref?.edit()
                    ?.putString(KEY, FILE_NAME)
                    ?.apply()
            }
        }
    }

    fun isLaunchFirstTime(): Boolean? {
        return sharedFirst?.getBoolean(LAUNCH_FLAG, true)
    }

    fun setAfterFirstLaunch() {
        sharedFirst?.edit()
            ?.putBoolean(LAUNCH_FLAG, false)
            ?.apply()
    }


    @RequiresApi(Build.VERSION_CODES.R)
    private suspend fun createMyFile() {
        coroutineScope {
            val free: Long = Environment.getStorageDirectory().freeSpace / 1024 / 1024
            if (free > 10) {
                val storagePath = context.getExternalFilesDir("MyStorage")
                myFile = File(storagePath, FILE_NAME)
            } else {
                Log.d("FileRepository", "System is out of Storage - $free < 10Mb")
            }
        }
    }

    companion object {
        private const val FILE_NAME = "1601841925_README.md"
        private const val KEY =
            "https://gitlab.skillbox.ru/learning_materials/android_basic/-/raw/master/README.md"
        private const val SHARED_NAME = "MySharedFile"
        private const val FIRST_LAUNCH_FLAG = "isFirstLaunch"
        private const val LAUNCH_FLAG = "first launch flag"
    }
}