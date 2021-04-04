package com.example.contentapp.custom_content

import android.content.*
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.example.contentapp.BuildConfig
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class CustomContentProvider : ContentProvider() {

    private lateinit var userPreferences: SharedPreferences
    private lateinit var coursesPreferences: SharedPreferences


    private val userAdapter: JsonAdapter<User> = Moshi.Builder().build().adapter(User::class.java)
    private val courseAdapter: JsonAdapter<Course> =
        Moshi.Builder().build().adapter(Course::class.java)

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_USERS, CODE_USERS)
        addURI(AUTHORITIES, PATH_COURSES, CODE_COURSES)
        addURI(AUTHORITIES, "${PATH_USERS}/#", CODE_USER_ID)
        addURI(AUTHORITIES, "${PATH_COURSES}/#", CODE_COURSE_ID)
    }

    override fun onCreate(): Boolean {
        Log.d("CustomContentProvider", "Content Provider Created")
        userPreferences = context!!.getSharedPreferences("user_shared_prefs", Context.MODE_PRIVATE)
        coursesPreferences =
            context!!.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d("CustomContentProvider", "Thread from Repository ${uri}")
        return when (uriMatcher.match(uri)) {
            CODE_COURSES -> {
                getAllCoursesFromCursor()
            }
            else -> null
        }
    }

    private fun getAllCoursesFromCursor(): Cursor {
        val allCourses = coursesPreferences.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }
        val matrixCursor = MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_NAME))
        allCourses.forEach {
            matrixCursor.newRow()
                .add(it.id)
                .add(it.title)
        }
        return matrixCursor
    }


    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d("CustomContentProvider", "Thread from Repository ${uri}")
        Log.d("CustomContentProvider", "Values ${values}")
        values ?: return null
        return when(uriMatcher.match(uri)) {
            CODE_COURSES -> {
                insertCourse(values)
            }
            CODE_COURSE_ID -> {
                getCourseLastSegment()
            }
            else -> null
        }
    }

    private fun insertCourse(contentValues: ContentValues): Uri? {
        val id = contentValues.getAsLong(COLUMN_COURSE_ID) ?: return null
        val title = contentValues.getAsString(COLUMN_COURSE_NAME) ?: return null
        val course = Course(id, title)
        coursesPreferences.edit()
            .putString(id.toString(),courseAdapter.toJson(course))
            .apply()
        Log.d("CustomContentProvider", "Uri InsertCourse ${Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")}")
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
    }

    private fun getCourseLastSegment(): Uri? {
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$CODE_COURSE_ID")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(uriMatcher.match(uri)) {
            CODE_COURSE_ID -> {
                deleteCourse(uri)
            }
            else -> -1
        }
    }

    private fun deleteCourse(uri: Uri): Int {
        val id = uri.lastPathSegment?.toLongOrNull() ?: return -1
       return if(coursesPreferences.contains(id.toString())) {
            coursesPreferences.edit()
                .remove(id.toString())
                .apply()
           1
        } else {
            return -1
        }


    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        values ?: return 1
        return when(uriMatcher.match(uri)) {
            CODE_COURSE_ID -> {
                updateCourse(uri, values)
            }
            else -> 0
        }
    }

    private fun updateCourse(uri: Uri, values: ContentValues): Int {
        val id = uri.lastPathSegment?.toLongOrNull() ?: return 0
       return if(coursesPreferences.contains(id.toString())) {
            insertCourse(values)
           1
        } else {
            0
       }
    }

    companion object {
        private const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PATH_USERS = "users"
        private const val PATH_COURSES = "courses"
        private const val CODE_USERS = 1
        private const val CODE_COURSES = 2
        private const val CODE_USER_ID = 3
        private const val CODE_COURSE_ID = 4

        private const val COLUMN_COURSE_ID = "id"
        private const val COLUMN_COURSE_NAME = "title"

    }

}