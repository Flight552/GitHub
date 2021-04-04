package com.example.customcontentapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val context: Context) {

    suspend fun getAllCourses(): List<Course> = withContext(Dispatchers.IO) {
        Log.d(
            "RepositorySaveCourse",
            "getAllCourses - Thread from Repository ${Thread.currentThread()}"
        )
        context.contentResolver.query(
            CUSTOM_AUTHORITY_URI,
            null,
            null,
            null,
            null
        )?.use {
            getCoursesFromCursor(it)
        }.orEmpty()
    }

    suspend fun addCourse(name: String, id: Long) = withContext(Dispatchers.IO) {
       // val courseID = saveRawCourse()
        saveCourseID(id, name)
    }

//    private fun saveRawCourse(): Long {
//       val uri: Uri? =  context.contentResolver.insert(
//            CUSTOM_AUTHORITY_URI,
//                ContentValues()
//        )
//        Log.d("RepositorySaveCourse", "saveRawCourse - uri: ${uri}")
//        return uri?.lastPathSegment?.toLongOrNull() ?: 0
//    }

    private fun saveCourseID(id: Long, name: String) {
        val contentValues = ContentValues().apply {
            put("id", id)
            put("title", name)
        }
        context.contentResolver.insert(
            CUSTOM_AUTHORITY_URI,
            contentValues
        )
    }


    private fun getCoursesFromCursor(cursor: Cursor): List<Course> {
        if (!cursor.moveToFirst()) return emptyList()
        val list: MutableList<Course> = mutableListOf()
        do {
            val idIndex = cursor.getColumnIndex("id")
            val id = cursor.getLong(idIndex)
            Log.d(
                "RepositorySaveCourse",
                "getCoursesFromCursor - cursor.getColumnIndex - ${idIndex}"
            )
            Log.d("RepositorySaveCourse", "getCoursesFromCursor - get cursor id - ${id}")
            val titleIndex = cursor.getColumnIndex("title")
            val title = cursor.getString(titleIndex)
            list.add(Course(id, title))
        } while (cursor.moveToNext())

        return list
    }

//    private suspend fun saveRawCourse(): Long {
//        var id = courseIDFromCursor()
//        return if (id == -1L) {
//            0
//        } else {
//            ++id
//        }
//    }

//    private suspend fun courseIDFromCursor(): Long = withContext(Dispatchers.IO) {
//        Log.d(
//            "RepositorySaveCourse",
//            "courseIDFromCursor - Thread from Repository ${Thread.currentThread()}"
//        )
//        context.contentResolver.query(
//            CUSTOM_AUTHORITY_URI,
//            null,
//            null,
//            null,
//            null
//        )?.use {
//            Log.d("RepositorySaveCourse", "courseIDFromCursor - get cursor - $it}")
//            getIDFromCursor(it)
//        }!!
//    }
//
//    private fun getIDFromCursor(cursor: Cursor): Long {
//        if (!cursor.moveToFirst()) return -1
//        cursor.moveToLast()
//        val index = cursor.getColumnIndex("id")
//        val id = cursor.getLong(index)
//        Log.d("RepositorySaveCourse", "getIDFromCursor - get cursor id - ${id}")
//        return id
//    }

    companion object {
        private val CUSTOM_AUTHORITY_URI: Uri =
            Uri.parse("content://com.example.contentapp.provider/courses")
    }
}