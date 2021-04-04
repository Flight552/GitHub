package com.example.customcontentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.customcontentapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var repository: Repository = Repository(this)
    private var courseList = emptyList<Course>()
    private val job = CoroutineScope(SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btGetCourseInfo.setOnClickListener {
            getContacts()
        }

        binding.btAddCourse.setOnClickListener {
            addCourse()
        }

    }

    private fun addCourse() {
        val course = binding.etAddCourse.text.toString()
        val id = binding.etAddID.text.toString()
        GlobalScope.launch {
            repository.addCourse(course, id.toLong())
        }
    }


    private fun getContacts() {
        job.launch(Dispatchers.IO) {
            val list = job.async {
                Log.d("Thread", "Thread from MainActivity ${Thread.currentThread()}")
                repository.getAllCourses()
            }
            showCourses(list.await())
        }
    }

    private fun showCourses(list: List<Course>) {
        job.launch(Dispatchers.Main) {
            binding.tvCourseInfo.text = list.toString()
        }

    }

}

