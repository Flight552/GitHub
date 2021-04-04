package com.skillbox.multithreading.threading

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.skillbox.multithreading.R
import com.skillbox.multithreading.adapters.MovieAdapter
import kotlinx.android.synthetic.main.fragment_threading.*

class ThreadingFragment : Fragment(R.layout.fragment_threading) {
    //add adapter
    private val viewModel: ThreadingViewModel by viewModels()
    private var movieAdapter: MovieAdapter? = null
    private lateinit var handler: Handler
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val backgroundThread = HandlerThread("HandlerThread").apply {
            start()
        }
        handler = Handler(backgroundThread.looper)

        init()

        swipe_layout.setOnRefreshListener {
            refreshMovies()
        }

        requestMovies.setOnClickListener {
           refreshMovies()
        }

        viewModel.time.observe(viewLifecycleOwner) {
            Log.d("ThreadTest", "livedata changed on ${Thread.currentThread().name}")
            timeTextView.text = it.toString()
        }
        viewModel.movies.observe(viewLifecycleOwner) {
            Log.d("ThreadTest", "adapter changed on ${Thread.currentThread().name}")
            movieAdapter?.updateList(it)
            movieAdapter?.notifyDataSetChanged()
        }
    }

    private fun init() {
        movieAdapter = MovieAdapter()
        with(rv_moviesList) {
            adapter = movieAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.looper.quit()
    }

    private fun refreshMovies() {
        swipe_layout.isRefreshing = true
        handler.post {
            viewModel.requestMovies()
            movieAdapter?.updateList(viewModel.getMoviesFromRepository())
            mainHandler.postDelayed({
                Toast.makeText(requireContext(), "The list is updated", Toast.LENGTH_LONG)
                    .show()
            }, 1000)
            swipe_layout.isRefreshing = false
        }
    }
}